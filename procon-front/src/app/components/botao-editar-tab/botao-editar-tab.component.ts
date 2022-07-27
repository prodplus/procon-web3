import { Component, Input, OnInit } from '@angular/core';
import { faEdit } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-editar-tab',
  templateUrl: './botao-editar-tab.component.html',
  styleUrls: ['./botao-editar-tab.component.css']
})
export class BotaoEditarTabComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagem: string = '';
  @Input() disable: boolean = false;
  iEdit = faEdit;

  constructor() { }

  ngOnInit(): void {
  }

}
