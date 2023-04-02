import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { CommentSchema } from './comment.model';
import {
  ClientsModule,
  Transport,
} from '@nestjs/microservices';

@Module({
  imports: [
    MongooseModule.forRoot(
      'mongodb+srv://roman:roman@cluster0.dhy6k.mongodb.net/?retryWrites=true&w=majority',
      { dbName: 'dev' },
    ),

    MongooseModule.forFeature([{ name: 'Comment', schema: CommentSchema }]),
  
    ClientsModule.register([
      {
        name: 'USER',
        transport: Transport.TCP,
        options: {
          port: 3000,
        },
      },
    ]),
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
