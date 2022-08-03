import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { ConsumidorDto } from 'src/app/models/dtos/consumidor-dto';

@Component({
  selector: 'app-tab-consumidores',
  templateUrl: './tab-consumidores.component.html',
  styleUrls: ['./tab-consumidores.component.css']
})
export class TabConsumidoresComponent implements OnInit {
  @Input() selecionando: boolean = false;
  @Input() consumidores: ConsumidorDto[] = [];
  @Output() selecionar = new EventEmitter<boolean>();
  iMinus = faMinus;

  constructor() { }

  ngOnInit(): void {
  }

  removerConsumidor(i: number) {
    this.consumidores.splice(i, 1);
  }

  mascara(tipo: string): string {
    return tipo === 'FISICA' ? '000.000.000-00' : '00.000.000/0000-00';
  }

  selecionarButton() {
    this.selecionar.emit(!this.selecionando);
  }

}
