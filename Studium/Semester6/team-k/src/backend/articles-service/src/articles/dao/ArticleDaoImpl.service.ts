import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Article, ArticleDocument } from 'src/articles/schemas/article.schema';
import { ArticleDao } from './ArticleDao';
import { Model } from 'mongoose';

/*
The Service implements the operations that interact with the database
*/
@Injectable()
export class ArticleDaoImplService implements ArticleDao {
  constructor(
    @InjectModel(Article.name)
    private articleModel: Model<ArticleDocument>,
  ) {}

  //TO-DO implement Methods
  async getArticlesFromDb() {
    var response;
    try {
      response = await this.articleModel.find().sort({_id:1});
      return response;
    } catch (error) {
        throw error
    }
  }

  async getArticleFromDb(articleId: string) {
    var response;
    try {
      response = await this.articleModel.findById(articleId);
    } catch (error) {
      throw error;
    }

    return response;

  }



}
