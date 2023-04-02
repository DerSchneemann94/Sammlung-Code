import { Controller, Get } from '@nestjs/common';
import { EventPattern, MessagePattern } from '@nestjs/microservices';
import { AppService } from './app.service';
import { of } from "rxjs";
import { delay } from "rxjs/operators";
import { CreateComment, DeleteComment, RateComment } from './comment.dto';

@Controller('')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @MessagePattern({ cmd: "ping" })
    ping(_: any) {
    return of("pong").pipe(delay(1000));
  } 

  @MessagePattern({ cmd: "postComment" })
    postComment(dto: CreateComment) {
    return this.appService.postComment(dto);
  } 

  @MessagePattern({ cmd: "getCommentsOfArticle" })
    getCommentsOfArticle(articleId: string) {
    return this.appService.getCommentsOfArticle(articleId);
  } 

  @MessagePattern({ cmd: "getCommentsOfUser" })
    getCommentsOfUser(userId: string) {
    return this.appService.getCommentsOfUser(userId);
  } 

  @MessagePattern({ cmd: "rateComment" })
    rateComment(dto: RateComment) {
    return this.appService.rateComment(dto);
  } 

  @MessagePattern({ cmd: "deleteComment" })
    deleteComment(dto: DeleteComment) {
    return this.appService.deleteComment(dto);
  }
}
