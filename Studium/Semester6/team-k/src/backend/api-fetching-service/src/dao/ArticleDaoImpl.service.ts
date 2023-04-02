import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Article, ArticleDocument } from 'src/schemas/Article.schema';
import { ArticleDao } from './ArticleDao';


/*
The Service implements the operations that interact with the database
*/
@Injectable()
export class ArticleDaoImplService implements ArticleDao {
  constructor(
    @InjectModel(Article.name)
    private articleModel: Model<ArticleDocument>,
  ) {}

  //TO-DO Implement Methods

  async getArticlesFromDb(): Promise<Array<Article & { _id: string }>> {
    return await (
      await this.articleModel.find()
    ).map((article) => {
      return article.toJSON();
    });
  }

  async updateArticlesInDb(newArticles: Array<Article>) {
    try {
      const newArticlesFormatted: Array<Article & { _id: string }> = [];
      for (let counter = 0; counter < newArticles.length; counter++) {
        const createdArticleMongoose = new this.articleModel(
          newArticles[counter],
        );
        await createdArticleMongoose.save();
        newArticlesFormatted.push(createdArticleMongoose.toJSON());
      }

      return newArticlesFormatted;
    } catch (error) {
      throw error;
    }
  }

  DeleteArticlesFromdb() {
    throw new Error('Method not implemented.');
  }
}
