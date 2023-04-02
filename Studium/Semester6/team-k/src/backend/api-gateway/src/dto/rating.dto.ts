import { ApiProperty } from '@nestjs/swagger';
import {
  IsNotEmpty,
  IsString,
} from 'class-validator';

export type RatingDtoChoice = 'UP' | 'DOWN';
export class RatingDto {
  @ApiProperty()
  @IsString()
  @IsNotEmpty()
  choice: RatingDtoChoice;
}
