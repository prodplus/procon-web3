import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UsuarioDto } from '../models/dtos/usuario-dto';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isLoading = false;
  user$: Observable<UsuarioDto | null> = new Observable<null>();

  constructor(private userService: UserService, private router: Router) {
    this.user$ = this.userService.getUser();
  }

  ngOnInit(): void { }

  proconsumidor() {
    window.open('https://proconsumidor.mj.gov.br', '_blank');
  }

}
