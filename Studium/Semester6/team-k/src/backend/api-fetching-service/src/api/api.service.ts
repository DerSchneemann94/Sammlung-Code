import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { ConfigService } from '@nestjs/config';
import { lastValueFrom } from 'rxjs';

/*
Launches API-Requests and Extracts the Data from the body
*/
@Injectable()
export class APIService {
  constructor(
    private httpService: HttpService,
    private configService: ConfigService,
  ) {}

  // API[0] = neuster Artikel
  async fetchArticlesFromAPI(options: Map<string, string> = null) {
    var params = {};
    try {
      const articles: any[] = (
        await lastValueFrom(
          this.httpService.get(this.configService.get('NEWS_API'), params),
        )
      ).data.articles;
      return articles;
    } catch (error) {
      throw error;
    }
  }
}
