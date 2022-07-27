import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-cancelar',
  templateUrl: './botao-cancelar.component.html',
  styleUrls: ['./botao-cancelar.component.css']
})
export class BotaoCancelarComponent implements OnInit {
  @Output() clique = new EventEmitter();
  iClose = faWindowClose;

  constructor() { }

  ngOnInit(): void {
  }

  onClick() {
    this.clique.emit();
  }

}
