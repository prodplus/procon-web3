import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faToggleOff, faToggleOn } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-ativar',
  templateUrl: './botao-ativar.component.html',
  styleUrls: ['./botao-ativar.component.css']
})
export class BotaoAtivarComponent implements OnInit {
  @Input() ativos: boolean = true;
  iToggleOn = faToggleOn;
  iToggleOff = faToggleOff;
  @Output() clique = new EventEmitter();

  constructor() { }

  ngOnInit(): void { }

  onClick() {
    this.clique.emit();
  }
}
