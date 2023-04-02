import { Injectable, Inject } from '@nestjs/common';
import { ClientProxy } from "@nestjs/microservices";
import { map } from "rxjs/operators";
import { CreateComment , RateComment, DeleteComment} from 'src/dto/comments.dto';

@Injectable()
export class CommentsService {
  constructor(
    @Inject("COMMENT_SERVICE") private readonly comment_service: ClientProxy
  ) {}
  
  pingCommentService() {
    const startTs = Date.now();
    const pattern = { cmd: "ping" };
    const payload = {};
    return this.comment_service
      .send(pattern, payload)
      .pipe(
        map((message: string) => ({ message, duration: Date.now() - startTs }))
      );
  }

  postComment(dto: CreateComment) {
    const pattern = { cmd: "postComment" };
    const payload = dto;
    return this.comment_service
      .send(pattern, payload);
  }

  getCommentsOfArticle(articleId: string,) {
    const pattern = { cmd: "getCommentsOfArticle" };
    const payload = articleId;
    return this.comment_service
      .send(pattern, payload);
  }

  getCommentsOfUser(userId: string) {
    const pattern = { cmd: "getCommentsOfUser" };
    const payload = userId;
    return this.comment_service
      .send(pattern, payload);
  }

  rateComment(dto: RateComment) {
    const pattern = { cmd: "rateComment" };
    const payload = dto;
    return this.comment_service
      .send(pattern, payload);
  }

  deleteComment(dto: DeleteComment) {
    const pattern = { cmd: "deleteComment" };
    const payload = dto;
    return this.comment_service
      .send(pattern, payload);
  }
}