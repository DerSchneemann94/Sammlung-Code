import { NestFactory } from '@nestjs/core';
import {
  MicroserviceOptions,
  Transport,
} from '@nestjs/microservices';
import { AppModule } from './app.module';

async function bootstrap() {
  const app =
    await NestFactory.createMicroservice<MicroserviceOptions>(
      AppModule,
      {
        transport: Transport.TCP,
        options: {
          port: 3000,
        },
      },
    );
  app.listen();
}
bootstrap();

export function bearerJWTTokenToJSON(
  bearerJWTToken: string,
): JSON {
  const base64Url = bearerJWTToken.split('.')[1];
  const base64 = base64Url
    .replace(/-/g, '+')
    .replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    Buffer.from(base64, 'base64')
      .toString('latin1')
      .split('')
      .map(function (c) {
        return (
          '%' +
          (
            '00' + c.charCodeAt(0).toString(16)
          ).slice(-2)
        );
      })
      .join(''),
  );
  return JSON.parse(jsonPayload);
}
