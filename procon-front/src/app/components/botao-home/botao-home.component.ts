import { Component, OnInit } from '@angular/core';
import { faHome } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-home',
  templateUrl: './botao-home.component.html',
  styleUrls: ['./botao-home.component.css']
})
export class BotaoHomeComponent implements OnInit {
  icon = faHome;

  constructor() { }

  ngOnInit(): void {
  }

}
