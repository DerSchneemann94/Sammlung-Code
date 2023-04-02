import { Injectable } from '@nestjs/common';
import { CacheService } from 'src/articles/cache/cache.service';
import { ArticleDaoImplService } from 'src/articles/dao/ArticleDaoImpl.service';
import { Article } from 'src/articles/schemas/article.schema';
import { FavoritesDto } from './dto/favorites.dto';

@Injectable()
export class FavoritesService {
  constructor(
    private articleDaoImplService: ArticleDaoImplService,
    private cacheService: CacheService
  ) {}

  async adjustUserFavorites(favoritesDto: FavoritesDto) {
    try {
      var operationSuccess = false;
      var favorite = false;
      var article =
        await this.articleDaoImplService.getArticleFromDb(
          favoritesDto.articleId,
        );
      var subscriber: string[] = article.subscriber;
      if (!subscriber.includes(favoritesDto.userId)) {
        subscriber.push(favoritesDto.userId);
        favorite = true;
      } else {
        subscriber.filter((userId) => {
          userId != favoritesDto.userId;
        });
      }
      article.subscriber = subscriber;
      await article.save();
      this.cacheService.adjustCache(article.toJSON());
      operationSuccess = true;
    } catch (error) {
      throw error(error);
    }

    return {
      articleId: favoritesDto.articleId,
      userId: favoritesDto.userId,
      operationSuccess: operationSuccess,
      favorite: favorite,
    };
  }
}
