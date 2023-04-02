import { UserDto } from 'src/dto/User.dto';
import { UserDocument } from 'src/schemas/user.schema';

export type MongoUser = UserDocument & {
  _id: string;
};

export interface UserDao {
  getUserFromDbViaEMail(
    email: string,
  ): Promise<Array<MongoUser>>;

  addUserToDb(dto: UserDto): Promise<any>;

  deleteUserFromDb(email: string);
}
