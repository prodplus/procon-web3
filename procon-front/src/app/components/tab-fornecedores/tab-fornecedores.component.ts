import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';

@Component({
  selector: 'app-tab-fornecedores',
  templateUrl: './tab-fornecedores.component.html',
  styleUrls: ['./tab-fornecedores.component.css']
})
export class TabFornecedoresComponent implements OnInit {
  @Input() selecionado: boolean = false;
  @Input() fornecedores: FornecedorDto[] = [];
  @Output() selecionar = new EventEmitter<boolean>();
  iMinus = faMinus;

  constructor() { }

  ngOnInit(): void {
  }

  removerFornecedor(i: number) {
    this.fornecedores.splice(i, 1);
  }

  selecionarButton() {
    this.selecionar.emit(!this.selecionado);
  }

}
