import {
  Body,
  Headers,
  Controller,
  Get,
  HttpException,
  HttpStatus,
  Param,
  Put,
  UseGuards,
} from '@nestjs/common';
import { ApiResponse, ApiTags } from '@nestjs/swagger';
import { userEditDto } from 'src/dto/userEdit.dto';
import { JwtGuard } from 'src/guard';
import { bearerJWTTokenToJSON } from 'src/main';
import { UserService } from './user.service';

@ApiTags('user')
@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}

  @ApiResponse({ status: 200, description: 'User info has been fetched successfully.'})
  @Get(':userId')
  async getUserInformation(
    @Param('userId') userId,
  ) {
    //TO-DO answer to User if successful or not
    return this.userService.getUserViaId(userId);
  }

  @ApiResponse({ status: 201, description: 'User has been edited successfully.'})
  @ApiResponse({ status: 401, description: 'Unauthorized.'})
  @Put('')
  @UseGuards(JwtGuard)
  async editUser(
    @Body() dto: userEditDto,
    @Headers('Authorization')
    bearerToken: string,
  ) {
    const jwtContent = bearerJWTTokenToJSON(
      bearerToken,
    ) as { email?: string };
    if (!jwtContent.email) {
      throw new HttpException(
        'Illegal Token',
        HttpStatus.BAD_REQUEST,
      );
    }

    this.userService.editUser(
      jwtContent.email,
      dto,
    );
  }
}
