import { IsEmail, IsNotEmpty, IsString } from "class-validator";

export class RatingDto {
    userId: string;
  
    @IsNotEmpty()
    choice: string;

    referencedId: string;

}