import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenDto } from '../models/dtos/token-dto';
import { LoginForm } from '../models/forms/login-form';
import { UserService } from './user.service';

const URL = environment.url + '/auth';

@Injectable({providedIn: 'root'})
export class AuthService {
  constructor(private http: HttpClient, private userService: UserService) { }

  authenticate(login: LoginForm) {
    return this.http.post<TokenDto>(`${URL}`, new LoginForm(login.email, login.password), {
      observe: 'response',
    }).pipe(tap((res) => {
      const authToken = res.body?.tipo + ' ' + res.body?.token;
      this.userService.setToken(authToken);
    }));
  }
}
