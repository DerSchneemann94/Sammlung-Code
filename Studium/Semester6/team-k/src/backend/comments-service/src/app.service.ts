import { Inject, Injectable, HttpException, HttpStatus } from '@nestjs/common';
import { map, of } from 'rxjs';
import { ClientProxy } from '@nestjs/microservices';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Comment } from './comment.model';
import { RateComment, DeleteComment, CreateComment } from './comment.dto';

@Injectable()
export class AppService {
  constructor(
    @InjectModel('Comment') private readonly commentModel: Model<Comment>,
    @Inject('USER') private readonly userClient: ClientProxy,
  ) {}

  async postComment(dto: CreateComment) {
    try {
      const newComment = new this.commentModel({
        articleId: dto.articleId,
        userId: dto.userId,
        parentId: dto.parentId,
        comment: dto.comment,
        upvotes: [],
        downvotes: [],
      });
      const comment = await newComment.save();
      return { id: comment.id };
    } catch (error) {
      throw error;
    }
  }


  async getCommentsOfArticle(articleId: string) {
    const commentsOfArticle = await this.commentModel
      .find({ articleId: articleId })
      .exec();

    // commentsOfArticle.map((comment) => this.userClient.send('info', comment.userId).forEach((val) => console.log(val)));

    try {
      let test = await new Promise(async (resolve) => {
        resolve(
          commentsOfArticle.map(async (commentMongo) => {
            const comment = commentMongo.toJSON();
            let user;
            await this.userClient
              .send('info', comment.userId)
              .forEach((fetchedUser) => (user = fetchedUser));
            const editedComment = {
              ...comment,
              user,
            };

            return editedComment;
          }),
        );
      });
      console.log('test', test);

      const comments = [];
      for (const promise of test as Array<any>) {
        comments.push(await promise);
      }
      return comments;
    } catch (e) {
      console.error('e', e);
      throw new HttpException(
        'comments could not be constructed',
        HttpStatus.BAD_REQUEST,
      );
    }
  } 
  
  async getCommentsOfUser(userId: string) {
    const commentsOfUser = await this.commentModel.find({userId: userId}).exec();
    let test = await new Promise(async (resolve)=>{
      resolve((commentsOfUser.map(async (comment) => {
        let user;
        console.log(comment.userId);
        await (this.userClient.send('info', comment.userId).forEach((fetchedUser) => user=fetchedUser));
        const editedComment={id: comment.id, articleId: comment.articleId, user: user, parentId: comment.parentId, comment: comment.comment, upvotes: comment.upvotes, downvotes: comment.downvotes, createdAt: comment.createdAt};
        return editedComment;
      }
      )));
    });
    const comments=[];
    for(const promise of test as Array<any>){
      comments.push(await promise);
      
    }  
    return (comments);
  } 

  // todo doppelt up löscht
  async rateComment(dto: RateComment) {
    try {
      let updatedComment = await this.commentModel.findById(dto.commentId);
  
      if(dto.rating === 'UP') {
        updatedComment.downvotes = updatedComment.downvotes.filter((userId) => userId != dto.userId);
        
        if(updatedComment.upvotes.length == updatedComment.upvotes.filter((userId) => userId != dto.userId).length) {
          updatedComment.upvotes.push(dto.userId);
        } else {
          updatedComment.upvotes = updatedComment.upvotes.filter((userId) => userId != dto.userId);
        }
      } else if (dto.rating === 'DOWN') {
        updatedComment.upvotes = updatedComment.upvotes.filter((userId) => userId != dto.userId);
        
        if(updatedComment.downvotes.length == updatedComment.downvotes.filter((userId) => userId != dto.userId).length) {
          updatedComment.downvotes.push(dto.userId);
        } else {
          updatedComment.downvotes = updatedComment.downvotes.filter((userId) => userId != dto.userId);
        }
      }
      updatedComment = await updatedComment.save();
      return of({
        id: updatedComment.id,
        articleId: updatedComment.articleId,
        userId: updatedComment.userId,
        parentId: updatedComment.parentId,
        comment: updatedComment.comment,
        upvotes: updatedComment.upvotes,
        downvotes: updatedComment.downvotes,
        createdAt: updatedComment.createdAt,
      });
    } catch (error) {
      throw new HttpException(
        `could not find comment with id ${dto.commentId}`,
        HttpStatus.NOT_FOUND,
    );
    }
  }

  async deleteComment(dto: DeleteComment) {
    try {
      let deletedComment = await this.commentModel.findById(dto.commentId);
      if (deletedComment.userId == dto.userId) {
        deletedComment.comment = 'This comment was deleted';
        deletedComment = await deletedComment.save();
      }
      return of({
        id: deletedComment.id,
        articleId: deletedComment.articleId,
        parentId: deletedComment.parentId,
        comment: deletedComment.comment,
        upvotes: deletedComment.upvotes,
        downvotes: deletedComment.downvotes,
        createdAt: deletedComment.createdAt,
      });
    } catch (error) {
      throw error;
    }
  }
}
