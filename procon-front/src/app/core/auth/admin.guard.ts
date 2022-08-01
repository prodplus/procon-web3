import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Injectable({providedIn: 'root'})
export class AdminGuard implements CanActivate {
  teste: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    if (this.userService.isLogged()) {
      this.userService.getUser().subscribe({
        next: (u) => (this.teste = u?.perfil === 'ROLE_ADMIN'),
        error: (err) => (console.log(err)),
      })
    }
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.teste) return true;
    else {
      this.router.navigate(['']);
      return false;
    }
  }
}
