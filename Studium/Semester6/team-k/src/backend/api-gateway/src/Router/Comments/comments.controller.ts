import { Param, Body, Controller, Delete, Get, Post, Put, UseGuards, HttpException, HttpStatus, Headers } from '@nestjs/common';
import { CommentsService } from './comments.service';
import { JwtGuard } from 'src/guard';
import { CreateComment, DeleteComment, RateComment } from 'src/dto/comments.dto';
import { bearerJWTTokenToJSON } from 'src/main';
import { response } from 'express';
import { map } from 'rxjs';
import { ApiResponse, ApiTags, ApiBearerAuth } from '@nestjs/swagger';

@ApiTags('comments')
@Controller("comments")
export class CommentsController {
  constructor(private readonly commentsService: CommentsService) {}

  
  @ApiResponse({ status: 201, description: 'successful operation'})
  @Get("/ping")
  pingCommentService() {
    return this.commentsService.pingCommentService();
  }

  @ApiBearerAuth()
  @ApiResponse({ status: 403, description: 'Illegal Token'})
  @UseGuards(JwtGuard)
  @Post("/article/:articleId")
  postComment(
    @Param('articleId') articleId: string,
    @Headers('Authorization') bearerToken: string,
    @Body() comment,
    ) {
    const dto: CreateComment = new CreateComment();
    const jwtContent = bearerJWTTokenToJSON(bearerToken) as {email?: string; userId?: string};
    
    if (!jwtContent.userId) {
        throw new HttpException(
            'Illegal Token',
            HttpStatus.BAD_REQUEST,
        );
    }

    dto.userId = jwtContent.userId;
    dto.articleId = articleId,
    dto.parentId = comment.parentId,
    dto.comment = comment.comment,
    console.log(`commentsDto: ${JSON.stringify(dto)}`);

    return this.commentsService.postComment(dto);
  }

  @Get("/article/:articleId")
  async getCommentsOfArticle(@Param('articleId') articleId: string) {
    return this.commentsService.getCommentsOfArticle(articleId);
  }

  @Get("/user/:userId")
  getCommentsOfUser(@Param('userId') userId: string) {
    return this.commentsService.getCommentsOfUser(userId);
  }

  @ApiBearerAuth()
  @ApiResponse({ status: 403, description: 'Illegal Token'})
  @UseGuards(JwtGuard)
  @Put("/rate/:commentId")
  rateComment(
    @Param('commentId') commentId: string,
    @Headers('Authorization') bearerToken: string,
    @Body('rating') rating: string,
    ) {
    const dto: RateComment = new RateComment();
    const jwtContent = bearerJWTTokenToJSON(bearerToken) as {email?: string; userId?: string};
    
    if (!jwtContent.userId) {
        throw new HttpException(
            'Illegal Token',
            HttpStatus.BAD_REQUEST,
        );
    }

    dto.userId = jwtContent.userId;
    dto.commentId = commentId;
    dto.rating = rating;
    console.log(`commentsDto: ${JSON.stringify(dto)}`);

    return this.commentsService.rateComment(dto);
  }

  @ApiBearerAuth()
  @ApiResponse({ status: 403, description: 'Illegal Token'})
  @UseGuards(JwtGuard)
  @Delete("/:commentId")
  deleteComment(
    @Param('commentId') commentId: string,
    @Headers('Authorization') bearerToken: string,
   ) {
    const dto: DeleteComment = new DeleteComment();
    const jwtContent = bearerJWTTokenToJSON(bearerToken) as { email?: string; userId?: string };
    
    if (!jwtContent.userId) {
        throw new HttpException(
        'Illegal Token',
        HttpStatus.BAD_REQUEST,
        );
    }

    dto.userId = jwtContent.userId;
    dto.commentId = commentId;
    console.log(`commentsDto: ${JSON.stringify(dto)}`);
    return this.commentsService.deleteComment(dto);
  }
}

// TODO: gets should return comment with user instead of user id ... -> fetch the other service here ...
