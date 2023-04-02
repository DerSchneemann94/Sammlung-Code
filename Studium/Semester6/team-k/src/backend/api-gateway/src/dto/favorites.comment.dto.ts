import { IsNotEmpty, IsString } from "class-validator";

export class FavoritsCommentDto {
    @IsString()
    @IsNotEmpty()
    description: string

}