import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { CacheService } from 'src/articles/cache/cache.service';
import { ArticleDaoImplService } from 'src/articles/dao/ArticleDaoImpl.service';
import { RatingDaoImplService } from './dao/ratingDaoImpl.service';

/*
Service to change the article ratings
*/
@Injectable()
export class RatingService {
  up: string;
  down: string;

  constructor(
    private ratingDao: RatingDaoImplService,
    private configService: ConfigService,
    private articleService: ArticleDaoImplService,
    private cacheService: CacheService
  ) {
    this.up = this.configService.get('UP');
    this.down = this.configService.get('DOWN');
  }

  async changeRating(choice: string, articleId: string, userId: string) {
    var operationSuccess;

    var response = {
      articleId: articleId,
      userId: userId,
      choice: choice,
    };

    try {
      var article = await this.articleService.getArticleFromDb(articleId);
      if (article == null) {
       throw new HttpException(
          'Article does not exist',
          HttpStatus.BAD_REQUEST)
      }
      this.modifyRatings(choice, userId, article);
      this.cacheService.adjustCache(article.toJSON());
      operationSuccess = true;
    } catch (error) {
      operationSuccess = false;
      console.error(error);
    }
    response['operationSuccess'] = operationSuccess;

    return response;
  }

  modifyRatings(choice: string, userId: string, article: any) {
    var res;
    try {
      switch (choice) {
        case this.down:
          res = this.testArray(article.dislikes, userId);
          if (res) {
            this.ratingDao.deleteRating(article, choice, userId);
          } else {
            this.ratingDao.addRating(article, choice, userId);
          }
          break;
        case this.up:
          res = this.testArray(article.likes, userId);
          if (res) {
            this.ratingDao.deleteRating(article, choice, userId);
          } else {
            this.ratingDao.addRating(article, choice, userId);
          }
          break;
      }
    } catch (error) {
      throw error;
    }
  }

  //helper function to check if an entry is the given array
  testArray(array: any[], item: any): boolean {
    for (let i = 0; i < array.length; i++) {
        if (array[i] == item) {
        return true;
      }
    }
    return false;
  }

  getArticleRatings(articleId: string) {
    try {
      this.ratingDao.getRating(articleId);
    } catch (error) {}
  }
}
