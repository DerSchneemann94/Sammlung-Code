import {
  BadRequestException,
  ForbiddenException,
  Injectable,
} from '@nestjs/common';
import * as argon from 'argon2';
import {
} from 'src/schemas/user.schema';
import {
  RegisterDto,
} from '../dto/User.dto';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { AuthDto } from 'src/dto/auth.dto';
import { UserDaoService } from 'dao/UserDaoImpl.service';

type LoginData = {
  access_token: string;
  username: string;
  id: string;
  image?: string;
};

@Injectable()
export class AuthenticationService {
  constructor(
    private jwt: JwtService,
    private config: ConfigService,
    private userDao: UserDaoService,
  ) {}

  async signup(
    dto: RegisterDto,
  ): Promise<LoginData> {
    const hash = await argon.hash(dto.password);

    const [user] =
      await this.userDao.getUserFromDbViaEMail(
        dto.email,
      );

    if (user) {
      throw new BadRequestException(
        'USER EXISTS',
      );
    }
    delete dto.password;
    dto['hash'] = hash;

    const createdUser =
      await this.userDao.addUserToDb(dto);

    return {
      ...(await this.signToken(
        createdUser._id,
        createdUser.email,
      )),

      username: createdUser.username,
      id: createdUser._id,
      image: user?.image,
    };
  }

  async signin(dto: AuthDto): Promise<LoginData> {
    // Password hashing
    const [user] =
      await this.userDao.getUserFromDbViaEMail(
        dto.email,
      );
    // compare password
    const pwMatches = await argon.verify(
      user.hash,
      dto.password,
    );
    // if password incorrect throw exception
    if (!pwMatches)
      throw new ForbiddenException(
        'Credentials incorrect',
      );
    // if password incorrect throw exception
    if (!pwMatches)
      throw new ForbiddenException(
        'Credentials incorrect',
      );

    return {
      ...(await this.signToken(
        user._id,
        user.email,
      )),

      username: user.username,
      id: user._id,
      image: user.image,
    };
  }
  catch(error) {
    throw error;
  }

  async signToken(
    userId: string,
    email: string,
  ): Promise<{ access_token: string }> {
    const payload = {
      userId,
      email,
    };
    const secret = this.config.get('JWT_SECRET');

    const token = await this.jwt.signAsync(
      payload,
      {
        // expiresIn: '15m',
        secret: secret,
      },
    );

    return {
      access_token: token,
    };
  }
}
