import { ParseBoolPipe } from '@nestjs/common';
import { Prop, raw, Schema, SchemaFactory, SchemaOptions } from '@nestjs/mongoose';

export type ArticleDocument = Article & Document;

@Schema()
class Source {
  @Prop()
  id: string;

  @Prop()
  name: string;
}

@Schema()
export class Article {
  
  @Prop()
  source: Source;

  @Prop()
  author: string;

  @Prop()
  title: string;

  @Prop()
  description: string;

  @Prop()
  url: string;

  @Prop()
  urlToImage: string;

  @Prop()
  publishedAt: string;

  @Prop()
  content: string;

  @Prop()
  likes: Array<string>;
  
  @Prop()
  dislikes: Array<string>;

  @Prop()
  subscriber: Array<string>;

}

export const ArticleSchema = SchemaFactory.createForClass(Article);