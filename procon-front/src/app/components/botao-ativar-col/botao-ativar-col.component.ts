import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-ativar-col',
  templateUrl: './botao-ativar-col.component.html',
  styleUrls: ['./botao-ativar-col.component.css']
})
export class BotaoAtivarColComponent implements OnInit {
  @Input() ativos: boolean = true;
  @Input() id!: number;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() clique = new EventEmitter<{ id: number; tipo: string }>();

  constructor() { }

  ngOnInit(): void {
  }

  onClick() {
    this.clique.emit({ id: this.id, tipo: this.ativos ? 'd' : 'a' });
  }

}
