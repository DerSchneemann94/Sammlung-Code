import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ArticleController } from './articles/article.controller';
import { ArticleService } from './articles/article.service';
import { MongooseModule } from '@nestjs/mongoose';
import {
  Article,
  ArticleSchema,
} from './articles/schemas/article.schema';
import { ConfigModule } from '@nestjs/config';
import { ArticleDaoImplService } from './articles/dao/ArticleDaoImpl.service';
import { CacheService } from './articles/cache/cache.service';
import { RatingController } from './rating/rating.controller';
import { RatingService } from './rating/rating.service';
import { RatingDaoImplService } from './rating/dao/ratingDaoImpl.service';
import { FavoritesController } from './favorites/favorites.controller';
import { FavoritesService } from './favorites/favorites.service';

@Module({
  imports: [
    MongooseModule.forRoot(
      'mongodb+srv://roman:roman@cluster0.dhy6k.mongodb.net/?retryWrites=true&w=majority',
      { dbName: 'devArticles' },
    ),
    MongooseModule.forFeature([
      { name: Article.name, schema: ArticleSchema },
    ]),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: ['.development.env'],
    }),
    HttpModule,
    ScheduleModule.forRoot(),
  ],
  controllers: [
    AppController,
    ArticleController,
    RatingController,
    FavoritesController,
  ],
  providers: [
    AppService,
    ArticleService,
    ArticleDaoImplService,
    CacheService,
    RatingService,
    RatingDaoImplService,
    FavoritesService,
  ],
})
export class AppModule {}
