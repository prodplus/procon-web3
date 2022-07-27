import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { faClose } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse } from 'src/app/models/auxiliares/exception-response';

import { RespModal } from 'src/app/models/auxiliares/resp-modal';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {
  mensagem: Mensagem = new Mensagem('', '', '');
  operacao: string = '';
  confirmar: boolean = false;
  iClose = faClose;
  @Output() sim = new EventEmitter<RespModal>();
  @ViewChild('modal')
  modal!: ElementRef;

  constructor(private modalService: NgbModal) { }

  ngOnInit(): void {
  }

  fechaModal() {
    this.modalService.dismissAll();
  }

  open(tipo: string, titulo: string, mensagem: string, operacao: string,
      confirmar: boolean) {
    this.mensagem = new Mensagem('modal-title text-' + tipo, titulo, mensagem);
    this.operacao = operacao;
    this.confirmar = confirmar;
    this.modalService.open(this.modal);
  }

  openErro(err: HttpErrorResponse) {
    this.mensagem = new Mensagem('modal-title text-danger', 'Erro!!', err.error.message);
    this.modalService.open(this.modal);
  }

  concordar() {
    this.sim.emit(new RespModal(true, this.operacao));
    this.fechaModal();
  }

}

class Mensagem {
  tipo: string;
  titulo: string;
  mensagem: string;

  constructor(tipo: string, titulo: string, mensagem: string) {
    this.tipo = tipo;
    this.titulo = titulo;
    this.mensagem = mensagem;
  }
}
