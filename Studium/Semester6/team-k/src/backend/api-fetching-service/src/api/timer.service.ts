import { Injectable } from '@nestjs/common';
import { Cron } from '@nestjs/schedule';
import { CacheService } from '../cache/cache.service';

/*
Service that defines frequent operations
*/
@Injectable()
export class TimerService {
  constructor(private cacheService: CacheService) {}


  //Updates the Cache every hour 
  @Cron('0 0 * * * *')
  async fetchNewsFromAPI() {
    this.cacheService.updateArticles();
  }
}
