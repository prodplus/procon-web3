import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';

import { UserService } from 'src/app/services/user.service';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.userService.isLogged()) return true;
    else {
      this.router.navigate(['']);
      return false;
    }
  }
}
