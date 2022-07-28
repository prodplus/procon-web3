import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { ModalComponent } from '../components/modal/modal.component';
import { Configuracao } from '../models/configuracao';
import { ConfiguracaoService } from '../services/configuracao.service';

@Component({
  selector: 'app-configuracoes',
  templateUrl: './configuracoes.component.html',
  styleUrls: ['./configuracoes.component.css']
})
export class ConfiguracoesComponent implements OnInit, AfterViewInit {
  isLoading = false;
  configuracao: Configuracao = new Configuracao();
  form!: FormGroup;
  @ViewChild('modal')
  modal!: ModalComponent;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;

  constructor(
    private configService: ConfiguracaoService,
    private builder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      descricao: [''],
      nome: [''],
      endereco: [''],
      diretoria: [''],
      email: ['']
    });

    this.isLoading = true;
    this.configService.buscar().subscribe({
      next: (c) => this.configuracao = c,
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.carregaFormulario(this.configuracao);
        this.isLoading = false;
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  private carregaFormulario(c: Configuracao) {
    this.form.patchValue({
      descricao: c.descricaoProcon,
      nome: c.nomeProcon,
      endereco: c.endereco,
      diretoria: c.diretoria,
      email: c.email
    });
  }

  private carregaConfiguracao(): Configuracao {
    return new Configuracao(this.form.get('descricao')?.value,
      this.form.get('nome')?.value, this.form.get('endereco')?.value,
      this.configuracao.fones, this.form.get('email')?.value,
      this.form.get('diretoria')?.value);
  }

  salvar() {
    this.isLoading = true;
    this.configService.salvar(this.carregaConfiguracao()).subscribe({
      next: (c) => (this.configuracao = c),
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.isLoading = false;
        this.modal.open('success', 'OK!', 'Configurações salvas com sucesso!', 'a', false);
      }
    });
  }

  cancelar() {
    this.router.navigate(['/']);
  }

}
