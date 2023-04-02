import {
  Body,
  Controller,
  Post,
} from '@nestjs/common';
import { ApiResponse, ApiTags } from '@nestjs/swagger';
import {
  AuthDto,
  RegisterDto,
} from 'src/dto/auth.dto';
import { AuthenticationService } from './authentication.service';

@ApiTags('authentication')
@Controller('authentication')
export class AuthenticationController {
  constructor(
    private authenticationService: AuthenticationService,
  ) {}

  @ApiResponse({ status: 201, description: 'User has been signed up succesfully.'})
  @Post('signup')
  async signUp(@Body() dto: RegisterDto) {
    return await this.authenticationService.signup(
      dto,
    );
  }

  @ApiResponse({ status: 201, description: 'User has been signed in successfully.'})
  @Post('signin')
  async signIn(
    @Body()
    dto: AuthDto,
  ) {
    return await this.authenticationService.signin(
      dto,
    );
  }
}
