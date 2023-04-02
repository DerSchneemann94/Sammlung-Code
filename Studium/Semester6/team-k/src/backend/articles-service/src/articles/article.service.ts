import { Injectable } from '@nestjs/common';
import { ArticleDaoImplService } from './dao/ArticleDaoImpl.service';



@Injectable()
export class ArticleService {


  constructor(private newsDao: ArticleDaoImplService){}

  async fetchNewsFromDb() {
    var articles;
    try {
      articles = await this.newsDao.getArticlesFromDb();  
    } catch (error) {
      throw error;
    }
  }

}
