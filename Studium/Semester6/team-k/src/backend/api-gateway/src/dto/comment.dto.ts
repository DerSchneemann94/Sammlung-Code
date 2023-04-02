import { IsString } from "class-validator";

export class CommentDto {

    @IsString()
    articleId: string;

    @IsString()
    commentId: string




}