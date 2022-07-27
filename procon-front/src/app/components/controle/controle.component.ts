import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-controle',
  templateUrl: './controle.component.html',
  styleUrls: ['./controle.component.css']
})
export class ControleComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagem: string = '';

  constructor() { }

  ngOnInit(): void {
  }

}
