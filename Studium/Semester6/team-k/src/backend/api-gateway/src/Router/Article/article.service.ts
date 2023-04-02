import {
  Inject,
  Injectable,
} from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { FavoritesArticleDto } from 'src/dto/favorites.article.dto';
import { RatingDtoChoice } from 'src/dto/rating.dto';

@Injectable()
export class ArticleService {
  constructor(
    @Inject('ARTICLES')
    private readonly articleClient: ClientProxy,
  ) {}

  fetchArticlesFromDb() {
    return this.articleClient.send(
      'fetch_Articles',
      {},
    );
  }

  fetchArticleFromDb(articleId: string) {
    return this.articleClient.send(
      'fetch_Article',
      articleId,
    );
  }

  changeArticleRatings(changeArticleRatingDTO) {
    return this.articleClient.send(
      'change_Article_Rating',
      changeArticleRatingDTO,
    );
  }

  changeFavorites(favoritesDto: FavoritesArticleDto) {
    return this.articleClient.send(
      'adjust_User_Favorites',
      favoritesDto,
    );
  }
}
