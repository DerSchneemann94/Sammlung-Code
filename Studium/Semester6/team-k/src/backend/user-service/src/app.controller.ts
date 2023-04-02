import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';

@Controller('')
export class AppController {
  constructor(
    private readonly appService: AppService,
  ) {}

  //Excdeption Handling für alle Fehler die auftreten können

  @Get('test')
  test() {
    //const response = this.appService.test();
  }
}
