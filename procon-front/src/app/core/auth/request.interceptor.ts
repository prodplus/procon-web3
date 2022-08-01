import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TokenService } from 'src/app/services/token.service';
import { environment } from 'src/environments/environment';

const DOMINIO = environment.url;

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
  constructor(private tokenService: TokenService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.tokenService.hasToken()) {
      const token = this.tokenService.getToken();
      if (req.url.startsWith(DOMINIO) && token != null) {
        req = req.clone({ setHeaders: { Authorization: token } });
      } else if (req.url.startsWith('https://www.receitaws.com.br')) {
        req = req.clone({
          setHeaders: {
            Authorization:
            '3c0ee2606911b798decc56ce9cd1a7030ef9a60a8ce1809e4723f90506166fe0'
          }
        });
      }
    }

    return next.handle(req);
  }
}

const URLExcecoes = [
  'https://viacep.com.br',
  'https://www.gstatic',
  'https://special.config',
  'https://www.receitaws.com.br',
  'https://proconsumidor'
];

function ehExcecao(url: string): boolean {
  for (let u of URLExcecoes) {
    if (url.startsWith(u, 0)) return true;
  }
  return false;
}
