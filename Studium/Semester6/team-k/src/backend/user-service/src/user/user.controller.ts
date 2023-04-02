import {
  Body,
  Headers,
  Controller,
  HttpException,
  HttpStatus,
  Post,
  UseGuards,
} from '@nestjs/common';
import { EventPattern } from '@nestjs/microservices';
import { userEditDto } from 'src/dto/userEdit.dto';
import { UserService } from './user.service';

@Controller('user')
export class UserController {
  constructor(private userService: UserService) {}

  /**
   *
   * @param body userId to identify the user
   * @returns image and username more information should not be available to the public
   */

  @EventPattern('info')
  async getUserInformation(
    @Body() userId: string,
  ) {
    //TO-DO answer to User if successfull or not
    try {
      const [userInformation] =
        await this.userService.getUserViaId(
          userId,
        );
      return {
        username: userInformation.username,
        image: userInformation.image,
      };
    } catch (error) {
      throw error;
    }
  }

  @EventPattern('edit')
  async editUser(@Body() dto: userEditDto) {
    await this.userService.editUserInDB(dto);
  }
}
