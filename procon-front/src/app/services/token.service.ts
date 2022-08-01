import { Injectable } from '@angular/core';

const KEY = 'authToken';

@Injectable({providedIn: 'root'})
export class TokenService {
  constructor() { }

  getToken() {
    return window.localStorage.getItem(KEY);
  }

  hasToken() {
    return !!this.getToken();
  }

  setToken(token: string) {
    window.localStorage.setItem(KEY, token);
  }

  removeToken() {
    window.localStorage.removeItem(KEY);
  }
}
