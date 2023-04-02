import {
  IsNotEmpty,
  IsString,
} from 'class-validator';

export class userEditDto {
  @IsString()
  @IsNotEmpty()
  uniqueMail: string;

  @IsString()
  @IsNotEmpty()
  image: string;
}
