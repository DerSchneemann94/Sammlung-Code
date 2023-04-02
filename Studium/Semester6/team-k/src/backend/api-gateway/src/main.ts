import { ValidationPipe } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import {
  DocumentBuilder,
  SwaggerModule,
} from '@nestjs/swagger';
import { AppModule } from './app.module';

//Function to extract Email and UserId from the jwt-token
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

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.useGlobalPipes(new ValidationPipe());
  app.enableCors();

  const config = new DocumentBuilder()
    .setTitle('News-App-Documentation')
    .setDescription('API Documentation for the Application News-App')
    .setVersion('1.0')
    .build();
  const document = SwaggerModule.createDocument(
    app,
    config,
  );
  SwaggerModule.setup('api', app, document);

  await app.listen(2999);
}
bootstrap();
