import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import jwtDecode from 'jwt-decode';
import { UsuarioDto } from '../models/dtos/usuario-dto';
import { TokenService } from './token.service';

@Injectable({providedIn: 'root'})
export class UserService {
  private userSubject = new BehaviorSubject<UsuarioDto | null>(null);
  private userName: string | null = null;
  private idUsuario: number | null = null;

  constructor(private tokenService: TokenService) {
    if (this.tokenService.hasToken()) this.decodeAndNotify();
  }

  setToken(token: string) {
    this.tokenService.setToken(token);
    this.decodeAndNotify();
  }

  getUsername(): string | null {
    return this.userName;
  }

  getIdUsuario(): number | null {
    return this.idUsuario;
  }

  getUser() {
    return this.userSubject.asObservable();
  }

  logout() {
    this.tokenService.removeToken();
    this.userSubject.next(null);
  }

  isLogged() {
    return !!this.userName;
  }

  private decodeAndNotify() {
    const token = this.tokenService.getToken();
    if (token != null) {
      const user = jwtDecode(token) as UsuarioDto;
      if (user.id != null) {
        this.userName = user.email;
        this.idUsuario = user.id;
        this.userSubject.next(user);
      } else {
        this.userName = null;
        this.userSubject.next(null);
      }
    }
  }
}
