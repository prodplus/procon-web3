import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isLoading = false;

  constructor() { }

  ngOnInit(): void { }

  proconsumidor() {
    window.open('https://proconsumidor.mj.gov.br', '_blank');
  }

}
