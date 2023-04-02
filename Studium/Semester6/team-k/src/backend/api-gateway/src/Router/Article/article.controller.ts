import {
  Body,
  Controller,
  Get,
  Header,
  Headers,
  HttpException,
  HttpStatus,
  Param,
  Post,
  UseGuards,
} from '@nestjs/common';
import { ApiResponse, ApiTags } from '@nestjs/swagger';
import { validate } from 'class-validator';
import { FavoritesArticleDto } from 'src/dto/favorites.article.dto';
import { RatingDto } from 'src/dto/rating.dto';
import { JwtGuard } from 'src/guard';
import { bearerJWTTokenToJSON } from 'src/main';
import { ArticleService } from './article.service';



@ApiTags('articles')
@Controller('articles')
export class ArticleController {
  constructor(
    private articleService: ArticleService,
  ) {}

  @ApiResponse({ status: 201, description: 'All Articles have been fetched successfully.'})
  @Get('')
  getArticles() {
    return this.articleService.fetchArticlesFromDb();
  }

  @ApiResponse({ status: 201, description: 'Article has been fetched successfully.'})
  @Get(':articleId')
  getArticle(
    @Param('articleId') articleId: string,
  ) {
    return this.articleService.fetchArticleFromDb(
      articleId,
    );
  }

  @ApiResponse({ status: 201, description: 'Rating of Article has been changed successfully.'})
  @ApiResponse({ status: 401, description: 'Unauthorized.'})
  @UseGuards(JwtGuard)
  @Post(':articleId/rate')
  async changeArticleRating(
    @Body() dto: RatingDto,
    @Param('articleId') articleId: string,
    @Headers('Authorization')
    bearerToken: string,
  ) {
    dto['id'] = articleId;
    const jwtContent = bearerJWTTokenToJSON(
      bearerToken,
    ) as { email?: string; userId?: string };
    if (!jwtContent.userId) {
      throw new HttpException(
        'Illegal Token',
        HttpStatus.BAD_REQUEST,
      );
    }
    return this.articleService.changeArticleRatings(
      { ...dto, userId: jwtContent.userId },
    );
  }
  
  @ApiResponse({ status: 201, description: 'Article favorites has been changed successfully.'})
  @ApiResponse({ status: 401, description: 'Unauthorized.'})
  @UseGuards(JwtGuard)
  @Post(':articleId/favorite')
  changeArticleFavorites(
    @Param('articleId') articleId: string,
    @Headers('Authorization')
    bearerToken: string,
  ) {
    var favoritesDto:FavoritesArticleDto = new FavoritesArticleDto();
    const jwtContent = bearerJWTTokenToJSON(
      bearerToken,
    ) as { email?: string; userId?: string };
    if (!jwtContent.userId) {
      throw new HttpException(
        'Illegal Token',
        HttpStatus.BAD_REQUEST,
      );
    }
    favoritesDto.userId = jwtContent.userId;
    favoritesDto.articleId = articleId;
    console.log('favoritesDto:   '+  JSON.stringify(favoritesDto))
    return this.articleService.changeFavorites(
      favoritesDto,
    );
  }
}
