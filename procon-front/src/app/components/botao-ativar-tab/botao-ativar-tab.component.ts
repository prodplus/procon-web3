import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-ativar-tab',
  templateUrl: './botao-ativar-tab.component.html',
  styleUrls: ['./botao-ativar-tab.component.css']
})
export class BotaoAtivarTabComponent implements OnInit {
  @Input() ativos: boolean = true;
  @Input() id: number | null = null;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() clique = new EventEmitter<{ id: number | null; tipo: string }>();

  constructor() { }

  ngOnInit(): void { }

  onClick() {
    this.clique.emit({ id: this.id, tipo: this.ativos ? 'd' : 'a' });
  }

}
