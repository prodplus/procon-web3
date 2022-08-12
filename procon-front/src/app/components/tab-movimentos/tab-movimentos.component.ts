import { Component, Input, OnInit } from '@angular/core';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { Movimento } from 'src/app/models/auxiliares/movimento';

@Component({
  selector: 'app-tab-movimentos',
  templateUrl: './tab-movimentos.component.html',
  styleUrls: ['./tab-movimentos.component.css']
})
export class TabMovimentosComponent implements OnInit {
  @Input() movimentacao: Movimento[] = [];
  iMinus = faMinus;

  constructor() { }

  ngOnInit(): void {
  }

  removerMov(i: number) {
    this.movimentacao.splice(i, 1);
  }

}
