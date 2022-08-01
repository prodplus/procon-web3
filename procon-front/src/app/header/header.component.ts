import { Component, OnInit } from '@angular/core';
import { faArrowRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import { Observable } from 'rxjs';

import { UsuarioDto } from '../models/dtos/usuario-dto';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user$: Observable<UsuarioDto | null> = new Observable<null>();
  iDoor = faArrowRightFromBracket;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.user$ = this.userService.getUser();
  }

  logout() {
    this.userService.logout();
  }

}
