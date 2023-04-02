import { HttpService } from '@nestjs/axios';
import { NestFactory } from '@nestjs/core';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { CacheService } from './cache/cache.service';
import { TimerService } from './api/timer.service';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.createMicroservice<MicroserviceOptions>(
    AppModule,
    {
      transport: Transport.TCP,
      options: {
        port: 3004
      }
    }
  );
  app.listen();
  const appService = app.get(CacheService);
  appService.initializeCache();
}
bootstrap();
