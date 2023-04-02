import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import {
  RegisterDto,
  UserDto,
} from 'src/dto/User.dto';
import {
  User,
  UserDocument,
} from 'src/schemas/user.schema';
import { MongoUser, UserDao } from './UserDao';
import { Model } from 'mongoose';
import { StringifyOptions } from 'querystring';



/*
Service that implements the methods to access the UserDatabase
*/
@Injectable()
export class UserDaoService implements UserDao {
  constructor(
    @InjectModel(User.name)
    private userModel: Model<UserDocument>,
  ) {}

  async getUserFromDbViaId(
    _id: string,
  ): Promise<Array<MongoUser>> {
    var user = null;
    try {
      user = await this.userModel.find({
        _id,
      });
    } catch (error) {
      throw error;
    }
    return user;
  }

  async getUserFromDbViaEMail(
    email: string,
  ): Promise<Array<MongoUser>> {
    var user = null;
    try {
      user = await this.userModel.find({
        email,
      });
    } catch (error) {
      throw error;
    }
    return user;
  }

  async addUserToDb(
    dto: RegisterDto,
  ): Promise<MongoUser> {
    var createdUser;
    try {
      createdUser = new this.userModel(dto);
      await createdUser.save();
    } catch (error) {
      throw error;
    }
    return createdUser;
  }

  deleteUserFromDb(email: string) {
    throw new Error('Method not implemented.');
  }
}
