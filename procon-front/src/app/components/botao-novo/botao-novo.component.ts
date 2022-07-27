import { Component, Input, OnInit } from '@angular/core';
import { faFile } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-novo',
  templateUrl: './botao-novo.component.html',
  styleUrls: ['./botao-novo.component.css']
})
export class BotaoNovoComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagem: string = '';
  icon = faFile;

  constructor() { }

  ngOnInit(): void {
  }

}
