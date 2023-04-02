import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';

import {
  User,
  UserDocument,
} from 'src/schemas/user.schema';
import { Model } from 'mongoose';
import { userEditDto } from 'src/dto/userEdit.dto';
import { UserDaoService } from 'dao/UserDaoImpl.service';
import { MongoUser } from 'dao/UserDao';

@Injectable()
export class UserService {
  constructor(
    private userDao: UserDaoService,
    @InjectModel(User.name)
    private userModel: Model<UserDocument>,
  ) {}

  async getUserViaId(
    _id: string,
  ): Promise<Array<MongoUser>> {
    var user = null;
    try {
      user =
        await this.userDao.getUserFromDbViaId(
          _id,
        );
    } catch (error) {
      throw error;
    }

    return user;
  }

  async getUserViaEMail(
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
  }

  async editUserInDB(dto: userEditDto) {
    const dbResponse =
      await this.userModel.findOne({
        email: dto.uniqueMail,
      });
    dbResponse.set('image', dto.image);
    dbResponse.save();
  }
}

type JSON = {
  [key: string]: string | JSON;
};
