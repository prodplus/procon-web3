import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-tabela',
  templateUrl: './tabela.component.html',
  styleUrls: ['./tabela.component.css']
})
export class TabelaComponent implements OnInit {
  @Input() tamanhoTotal: number = 0;
  @Input() tamanhoPagina: number = 0;
  @Input() pagina: number = 0;
  @Output() mudaPagina = new EventEmitter<number>();

  constructor() { }

  ngOnInit(): void {
  }

  alteraPagina(pagina: number) {
    this.pagina = pagina;
    this.mudaPagina.emit(this.pagina);
  }

}
