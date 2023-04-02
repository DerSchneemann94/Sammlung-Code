import { Module } from '@nestjs/common';
import {
  ClientsModule,
  Transport,
} from '@nestjs/microservices';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@nestjs/config';
import { JwtModule } from '@nestjs/jwt';
import { JwtStrategy } from './strategy/jwt.strategy';
import { ArticleController } from './Router/Article/article.controller';
import { AuthenticationController } from './Router/User/authentication.controller';
import { UserController } from './Router/User/user.conroller';
import { ArticleService } from './Router/Article/article.service';
import { UserService } from './Router/User/user.service';
import { AuthenticationService } from './Router/User/authentication.service';
import { CommentsController } from './Router/Comments/comments.controller';
import { CommentsService } from './Router/Comments/comments.service';

@Module({
  imports: [
    JwtModule.register({}),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.development.env',
    }),
    ClientsModule.register([
      {
        name: 'USER',
        transport: Transport.TCP,
        options: {
          port: 3000,
        },
      },
      {
        name: 'ARTICLES',
        transport: Transport.TCP,
        options: {
          port: 3001,
        },
      },
      {
        name: 'COMMENT_SERVICE',
        transport: Transport.TCP,
        options: {
          port: 3002,
        },
      },
    ]),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: ['.development.env'],
    }),
  ],
  controllers: [
    AppController,
    ArticleController,
    AuthenticationController,
    UserController,
    CommentsController,
  ],
  providers: [
    AppService,
    JwtStrategy,
    ArticleService,
    UserService,
    AuthenticationService,
    CommentsService,

  ],
})
export class AppModule {}
