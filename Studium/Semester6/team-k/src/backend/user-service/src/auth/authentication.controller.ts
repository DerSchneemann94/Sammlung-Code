import {
  Body,
  Controller,
  Post,
} from '@nestjs/common';
import { AuthenticationService } from './authentication.service';
import { AuthDto } from '../dto/auth.dto';
import {
  RegisterDto,
  UserDto,
} from '../dto/User.dto';
import { EventPattern } from '@nestjs/microservices';

@Controller('')
export class AuthenticationController {
  constructor(
    private authenticationService: AuthenticationService,
  ) {}

  @EventPattern('sign_in')
  async signIn(
    @Body()
    dto: AuthDto,
  ) {
    console.log('sign_in', dto);

    return await this.authenticationService.signin(
      dto,
    );
  }

  @EventPattern('sign_up')
  async signUp(@Body() dto: RegisterDto) {
    console.log('sign_up', dto);

    return await this.authenticationService.signup(
      dto,
    );
  }

  /*   @Post('signin')
  async signIn(
    @Body()
    dto: {
      email: string;
      password: string;
    },
  ) {
    return await this.authenticationService.signin(
      dto.email,
      dto.password,
    );
  } */
}
