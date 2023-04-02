import { Inject, Injectable } from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { ArticleDaoImplService } from 'src/dao/ArticleDaoImpl.service';
import { Article } from 'src/schemas/Article.schema';
import { APIService } from '../api/api.service';


/*
In memory cache to compare the articles fetched from the API-response with the already available articles to determine the new articles 
*/
@Injectable()
export class CacheService {
  public cachedArticles: Array<
    Article & { _id: string }
  >;

  constructor(
    private apiService: APIService,
    private articleDAO: ArticleDaoImplService,
    @Inject('NEWS')
    private readonly newsClient: ClientProxy,
  ) {
    this.cachedArticles = [];
  }

  async updateArticles() {
    const articles =
      await this.articleDAO.updateArticlesInDb(
        this.calculateArticleDifferences(
          this.calculateArticleDifferences(
            await this.apiService.fetchArticlesFromAPI(),
            this.cachedArticles,
          ),
          await this.articleDAO.getArticlesFromDb(),
        ),
      );

    if (articles != null && articles.length > 0) {
      this.sendChangesToNewsService(
        articles,
      );
    }

    for (const article of articles) {
      this.cachedArticles.push(article);
    }
    console.log('updateCache done');
  }

  async initializeCache() {
    try {
      this.cachedArticles =
        await this.articleDAO.getArticlesFromDb();

      this.updateArticles();
    } catch (error) {
      throw error;
    }
  }

  calculateArticleDifferences(
    lhs: Array<Article>,
    rhs: Array<Article>,
  ): Array<Article> {
    const mostRecentlyPublished: string =
      rhs.length == 0
        ? '1000-01-01T00:00:00Z'
        : rhs[rhs.length - 1].publishedAt;
    const mostRecentlyPublishedDate: Date =
      new Date(mostRecentlyPublished);

    const newFetchedArticles = [];
    for (const newArticle of lhs) {
      let newArticleReleaseDate: Date = new Date(
        newArticle.publishedAt,
      );
      if (
        newArticleReleaseDate.getTime() >
        mostRecentlyPublishedDate.getTime()
      ) {
        newFetchedArticles.unshift(newArticle);
      }
    }

    return newFetchedArticles.filter(
      (article) => {
        for (const cachedArticle of this
          .cachedArticles) {
          if (!cachedArticle.content) continue;
          if (
            !article.content ||
            article.content.localeCompare(
              cachedArticle.content,
            ) === 0
          ) {
            return false;
          }
        }
        return true;
      },
    );
  }

  //The new articles fetched from the api are send to the news-services
  sendChangesToNewsService(articles: Article[]) {
    this.newsClient.emit('update_on_database', articles);
  }

}
