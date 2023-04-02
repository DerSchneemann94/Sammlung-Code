import { Injectable } from '@nestjs/common';

@Injectable()
export class AppService {
  getSelectionMenu(): string {
    return '<html><body><a href="http://kranserver.online/newsy">NEWSY</a><br><a href="./api">API DOCUMENT</a></body></html>';
  }
}
