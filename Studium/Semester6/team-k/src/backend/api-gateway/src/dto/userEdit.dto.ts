import { ApiProperty } from '@nestjs/swagger';
import {
  IsNotEmpty,
  IsString,
} from 'class-validator';

export class userEditDto {
  @ApiProperty()
  @IsString()
  @IsNotEmpty()
  image: string;
}
