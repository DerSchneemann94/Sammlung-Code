import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthenticationController } from './auth/authentication.controller';
import { AuthenticationService } from './auth/authentication.service';
import {
  User,
  UserSchema,
} from './schemas/user.schema';
import { ConfigModule } from '@nestjs/config';
import { JwtModule } from '@nestjs/jwt';
import { UserController } from './user/user.controller';
import { UserService } from './user/user.service';
import { UserDaoService } from '../dao/UserDao.service';
import { JwtStrategy } from './strategy/jwt.strategy';

@Module({
  imports: [
    JwtModule.register({}),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.development.env',
    }),
    MongooseModule.forRoot(
      'mongodb+srv://roman:roman@cluster0.dhy6k.mongodb.net/?retryWrites=true&w=majority',
      { dbName: 'dev' },
    ),
    MongooseModule.forFeature([
      { name: User.name, schema: UserSchema },
    ]),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: ['.development.env'],
    }),
  ],
  controllers: [
    AppController,
    AuthenticationController,
    UserController,
  ],
  providers: [
    AppService,
    AuthenticationService,
    JwtStrategy,
    UserService,
    UserDaoService,
  ],
})
export class AppModule {}
