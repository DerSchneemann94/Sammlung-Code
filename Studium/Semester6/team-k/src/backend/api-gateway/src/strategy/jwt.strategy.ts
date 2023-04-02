import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { InjectModel } from '@nestjs/mongoose';
import { PassportStrategy } from '@nestjs/passport';
import {
  ExtractJwt,
  Strategy,
} from 'passport-jwt';
import { Model } from 'mongoose';
import { Payload } from '@nestjs/microservices';

/*
The JwtStrategy realises the standard jwt-token authentication strategy provided by the passport module 
*/
@Injectable()
export class JwtStrategy extends PassportStrategy(
  Strategy,
  'jwt',
) {
  constructor(
    config: ConfigService,
  ) {
    super({
      jwtFromRequest:
        ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: config.get('JWT_SECRET'),
    });
  }


  //is performed before the values in the bodies are extracted
  async validate(payload) {
    return payload;
  }

}
