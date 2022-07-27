import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-controle-ativos',
  templateUrl: './controle-ativos.component.html',
  styleUrls: ['./controle-ativos.component.css']
})
export class ControleAtivosComponent implements OnInit {
  @Input() rota: string[] = [];
  @Input() mensagemNovo: string = '';
  @Input() ativos: boolean = true;
  @Output() clicou = new EventEmitter();

  constructor() { }

  ngOnInit(): void { }

  onClick() {
    this.clicou.emit();
  }

}
