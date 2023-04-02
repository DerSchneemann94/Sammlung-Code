import { Controller, UseGuards } from '@nestjs/common';
import { EventPattern } from '@nestjs/microservices';
import { RatingService } from './rating.service';

@Controller('')
export class RatingController {
  constructor(private ratingService: RatingService) {}

  @EventPattern('change_Article_Rating')
  changeRating(data: any) {
    return this.ratingService.changeRating(data.choice, data.id, data.userId);
    
  }

  @EventPattern('get_article_Rating')
  getArticleRatings(data: any) {
    return this.ratingService.getArticleRatings(data.dto);
  }
}
