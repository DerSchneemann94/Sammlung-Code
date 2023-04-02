import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { APIService } from './api/api.service';
import { ScheduleModule } from '@nestjs/schedule';
import { ConfigModule } from '@nestjs/config';
import { HttpModule } from '@nestjs/axios';
import { TimerService } from './api/timer.service';
import { CacheService } from './cache/cache.service';
import { MongooseModule } from '@nestjs/mongoose';
import {
  Article,
  ArticleSchema,
} from './schemas/Article.schema';
import {
  ClientsModule,
  Transport,
} from '@nestjs/microservices';
import { ArticleDaoImplService } from './dao/ArticleDaoImpl.service';

@Module({
  imports: [
    ScheduleModule.forRoot(),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: ['.development.env'],
    }),
    MongooseModule.forRoot(
      'mongodb+srv://roman:roman@cluster0.dhy6k.mongodb.net/?retryWrites=true&w=majority',
      { dbName: 'devArticles' },
    ),
    MongooseModule.forFeature([
      {
        name: Article.name,
        schema: ArticleSchema,
      },
    ]),
    HttpModule,
    ClientsModule.register([
      {
        name: 'NEWS',
        transport: Transport.TCP,
        options: {
          port: 3001,
        },
      },
    ]),
  ],
  controllers: [AppController],
  providers: [
    AppService,
    APIService,
    TimerService,
    ArticleDaoImplService,
    CacheService,
  ],
})
export class AppModule {}
