import {
  BadRequestException,
  ForbiddenException,
  Inject,
  Injectable,
} from '@nestjs/common';
import * as argon from 'argon2';
import { Model } from 'mongoose';
import { InjectModel } from '@nestjs/mongoose';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { UserDto } from 'src/dto/User.dto';
import { ClientProxy } from '@nestjs/microservices';
import {
  AuthDto,
  RegisterDto,
} from 'src/dto/auth.dto';

type LoginData = {
  access_token: string;
  username: string;
  id: string;
  image?: string;
};

@Injectable()
export class AuthenticationService {
  constructor(
    @Inject('USER')
    private readonly userClient: ClientProxy,
  ) {}

  async signup(dto: RegisterDto) {
    return this.userClient.send('sign_up', dto);
  }

  async signin(dto: AuthDto) {
    return this.userClient.send('sign_in', dto);
  }
}
