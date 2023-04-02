import { AuthGuard } from '@nestjs/passport';

/*
The JWT-Guard uses the jwt-strategy to check all annotated routes (@UseGuards('JwtGuard') to check if the jwt token of the request is available/valid)
*/
export class JwtGuard extends AuthGuard('jwt') {
  constructor() {
    super();
  }
} 
