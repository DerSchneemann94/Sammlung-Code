import { Controller, Get } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { EventPattern } from '@nestjs/microservices';
import { lastValueFrom } from 'rxjs';
import { ArticleService } from './article.service';
import { HttpService } from '@nestjs/axios';
import { Article } from 'src/articles/schemas/article.schema';
import { CacheService } from 'src/articles/cache/cache.service';

@Controller('')
export class ArticleController {
  constructor(
    private newsService: ArticleService,
    private configService: ConfigService,
    private httpService: HttpService,
    private cacheService: CacheService,
  ) {}

  @EventPattern('fetch_Articles')
  getArticlesFromCache() {
    return this.cacheService.getArticlesFromCache();
  }

  @EventPattern('fetch_Article')
  getArticleFromCache(articleId: string) {
    return this.cacheService.getArticleFromCache(articleId);
  }

  //TO-DO: Delete later
  @EventPattern('test')
  async checkNewsAPI() {
    const url = this.configService.get('NEWS_API');
    const newArticles = await lastValueFrom(
      this.httpService.get(url),
    );
  }

  @EventPattern('update_on_database')
  updateArticle(data: any) {
    this.cacheService.updateCache(data.articles);
  }
}
