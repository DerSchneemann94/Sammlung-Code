import {
  IsNotEmpty,
  IsString,
} from 'class-validator';

export type RatingDtoChoice = 'UP' | 'DOWN';
export class RatingDto {
  constructor(
    choice: RatingDtoChoice,
    id: string,
  ) {
    this.choice = choice;
    this.id = id;
  }

  @IsString()
  @IsNotEmpty()
  choice: RatingDtoChoice;

  @IsString()
  @IsNotEmpty()
  id: string;
}
