import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { ConfigService } from '@nestjs/config';
import { Article, ArticleDocument } from 'src/articles/schemas/article.schema';
import { RatingDao } from './ratingDao';

@Injectable()
export class RatingDaoImplService implements RatingDao {
  up: string;
  down: string;

  constructor(
    @InjectModel(Article.name) private articleModel: Model<ArticleDocument>,
    private config: ConfigService,
  ) {
    this.up = this.config.get('UP');
    this.down = this.config.get('DOWN');
  }

  addRating(article: any, choice: any, userId: string) {
    try {
      switch (choice) {
        case this.up:
          article.likes.push(userId);
          article.dislikes = this.deleteOperation(article.dislikes, userId);
          break;
        case this.down:
          article.dislikes.push(userId);
          article.likes = this.deleteOperation(article.likes, userId);
          break;
      }
      article.save();
    } catch (error) {
      throw error;
    }
  }

  deleteRating(article: any, choice: any, userId: string) {
    try {
      switch (choice) {
        case this.up:
          article.likes = this.deleteOperation(article.likes, userId);
          break;
        case this.down:
          article.dislikes = this.deleteOperation(article.dislikes, userId);
          break;
      }
      article.save();
    } catch (error) {
      throw error;
    }
  }

  deleteOperation(ratingArray: string[], userId): string[] {
    var newRatingArray = ratingArray.filter((user) => user != userId);
    return newRatingArray;
  }

  async getRating(id: string): Promise<any> {
    var rating;
    try {
      rating = await this.articleModel.findOne({ referencedId: id });
    } catch (error) {
      throw error;
    }
    return rating;
  }
}
