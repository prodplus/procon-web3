import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-botao-excluir-tab',
  templateUrl: './botao-excluir-tab.component.html',
  styleUrls: ['./botao-excluir-tab.component.css']
})
export class BotaoExcluirTabComponent implements OnInit {
  @Input() id: number | null = null;
  @Input() disable: boolean = false;
  @Output() clique = new EventEmitter<{ id: number | null; tipo: string }>();
  iTrash = faTrash;

  constructor() { }

  ngOnInit(): void { }

  onClick() {
    this.clique.emit({ id: this.id, tipo: 'e' });
  }

}
