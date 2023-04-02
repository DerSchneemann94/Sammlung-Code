import { Injectable } from '@nestjs/common';
import { ArticleDaoImplService } from 'src/articles/dao/ArticleDaoImpl.service';
import { Article } from 'src/articles/schemas/article.schema';
import {
  Cron,
  CronExpression,
} from '@nestjs/schedule';

/*
In memory cache to increase the APIs response speed
*/
@Injectable()
export class CacheService {
  cachedArticles: any[] = [];

  constructor(
    private articleDao: ArticleDaoImplService,
  ) {}


  //Once Everyday the System loads the articles from the Database to fix potential errors in the cache 
  @Cron(CronExpression.EVERY_DAY_AT_4AM)
  async loadCacheWithDatabase() {
    var response;
    try {
      response =
        await this.articleDao.getArticlesFromDb();
      if (response != null) {
        this.cachedArticles = response;
      }
    } catch (error) {
      //TO-DO
      throw error;
    }
  }

  getArticlesFromCache() {
    return this.cachedArticles;
  }

  getArticleFromCache(articleId: string) {
    const article = this.cachedArticles.filter((article) => {
      articleId == article._id;
    });

    if(!article) {
      return {
       'response':'article not found'
      }
    }

    return article;
  }

  updateCache(articles: Article[]) {
    console.log(articles.length);
    this.cachedArticles =
      this.cachedArticles.concat(articles);
  }

  adjustCache(article: any) {
    const index = this.cachedArticles.findIndex(
      (cachedArticle) =>
        cachedArticle._id == article._id.toString(),
    );
    if (index < 0) {
      console.error('adjust Article not found');
      return;
    }
    this.cachedArticles[index] = article;
  }
}
