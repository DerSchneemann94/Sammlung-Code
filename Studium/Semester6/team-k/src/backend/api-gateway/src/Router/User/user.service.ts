import {
  Inject,
  Injectable,
} from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { userEditDto } from 'src/dto/userEdit.dto';

@Injectable()
export class UserService {
  constructor(
    @Inject('USER')
    private readonly userClient: ClientProxy,
  ) {}

  async getUserViaId(_id: string) {
    return this.userClient.send('info', _id);
  }

  /* async getUserViaEMail(
    email: string,
  ): Promise<Array<MongoUser>> {
    var user = null;
    try {
      user =
        await this.userDao.getUserFromDbViaEMail(
          email,
        );
    } catch (error) {
      throw error;
    }

    return user;
  } */

  async editUser(
    uniqueMail: string,
    dto: userEditDto,
  ) {
    return this.userClient.emit('edit', {
      ...dto,
      uniqueMail,
    });
  }
}

type JSON = {
  [key: string]: string | JSON;
};
