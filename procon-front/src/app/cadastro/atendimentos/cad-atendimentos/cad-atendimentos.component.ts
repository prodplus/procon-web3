import { Location } from '@angular/common';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Consumidor } from 'src/app/models/consumidor';
import { AtendimentoDto } from 'src/app/models/dtos/atendimento-dto';
import { ConsumidorDto } from 'src/app/models/dtos/consumidor-dto';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';
import { AtendimentoForm } from 'src/app/models/forms/atendimento-form';
import { Fornecedor } from 'src/app/models/fornecedor';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { UserService } from 'src/app/services/user.service';
import { toDateApi } from 'src/app/utils/date-utils';
import { getInputClass } from 'src/app/utils/validation';

@Component({
  selector: 'app-cad-atendimentos',
  templateUrl: './cad-atendimentos.component.html',
  styleUrls: ['./cad-atendimentos.component.css']
})
export class CadAtendimentosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  selecionandoCons = false;
  selecionandoForn = false;
  editandoCons = false;
  editandoForn = false;
  idConsumidor: number | null = null;
  idFornecedor: number | null = null;
  idAtendimento: number | null = null;
  atendimento!: AtendimentoDto;
  form!: FormGroup;
  iMinus = faMinus;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  @ViewChild('scrollCons')
  scrollConsumidor!: ElementRef<HTMLDivElement>;
  @ViewChild('scrollForn')
  scrollFornecedor!: ElementRef<HTMLDivElement>;
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private atendimentoService: AtendimentoService,
    private builder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      data: [toDateApi(new Date()), [Validators.required]],
      relato: [''],
    });

    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.atendimento = this.route.snapshot.data['atendimento'];
        this.carregaForm(this.atendimento);
      } else {
        this.atendimento = {
          id: 0,
          consumidores: [],
          fornecedores: [],
          data: toDateApi(new Date()),
          atendente: 0,
        }
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  private carregaForm(ate: AtendimentoDto) {
    this.isLoading = true;
    this.idAtendimento = ate.id;
    this.form.get('data')?.setValue(ate.data);
    this.atendimentoService.getRelato(ate.id).subscribe({
      next: (r) => (this.form.get('relato')?.setValue(r.relato)),
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => this.isLoading = false,
    });
  }

  private transformaCons(cons: ConsumidorDto[]): number[] {
    let retorno: number[] = [];
    cons.forEach((c) => retorno.push(c.id));
    return retorno;
  }

  private transformaForn(forn: FornecedorDto[]): number[] {
    let retorno: number[] = [];
    forn.forEach((f) => retorno.push(f.id));
    return retorno;
  }

  private carregaAtendimento(): AtendimentoForm {
    let idUsuario: number | null = this.userService.getIdUsuario();
    if (idUsuario == null) idUsuario = 0;
    return new AtendimentoForm(this.idAtendimento,
      toDateApi(new Date(this.form.get('data')?.value)),
      this.transformaCons(this.atendimento.consumidores),
      this.transformaForn(this.atendimento.fornecedores),
      this.form.get('relato')?.value,
      idUsuario);
  }

  salvar() {
    this.isLoading = false;
    if (this.idAtendimento == null) {
      this.atendimentoService.salvar(this.carregaAtendimento()).subscribe({
        next: (a) => (this.atendimento = a),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => {
          this.isLoading = false;
          this.router.navigate(['/cadastros/atendimentos']);
        }
      });
    } else if (this.idAtendimento != null) {
      this.atendimentoService
        .atualizar(this.idAtendimento, this.carregaAtendimento())
        .subscribe({
          next: (a) => (this.atendimento = a),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => {
            this.isLoading = false;
            this.router.navigate(['/cadastros/atendimentos']);
          }
        });
    }
  }

  selecionandoCEvent(e: boolean) {
    this.selecionandoCons = e;
  }

  selecionandoFEvent(e: boolean) {
    this.selecionandoForn = e;
  }

  consumidorSelecionado(cons: ConsumidorDto | null) {
    this.selecionandoCons = false;
    if (cons != null) this.idConsumidor = cons.id;
    else this.idConsumidor = null;
    this.editandoCons = true;
  }

  fornecedorSelecionado(forn: FornecedorDto | null) {
    this.selecionandoForn = false;
    if (forn != null) this.idFornecedor = forn.id;
    else this.idFornecedor = null;
    this.editandoForn = true;
  }

  novoFornecedor(e: boolean) {
    this.selecionandoForn = false;
    if (e) {
      this.idFornecedor = null;
      this.editandoForn = true;
    }
  }

  novoConsumidor(e: boolean) {
    this.selecionandoCons = false;
    if (e) {
      this.idConsumidor = null;
      this.editandoCons = true;
    }
  }

  getMascara(tipo: string): string {
    return tipo === 'FISICA' ? '000.000.000-00' : '00.000.000/0000-00';
  }

  removerConsumidor(i: number) {
    this.atendimento.consumidores.splice(i, 1);
  }

  removerFornecedor(i: number) {
    this.atendimento.fornecedores.splice(i, 1);
  }

  consumidorSalvo(cons: Consumidor | null) {
    this.editandoCons = false;
    if (cons != null) {
      if (this.atendimento.consumidores.length > 0) {
        let index = -1;
        for (let c of this.atendimento.consumidores) {
          if (c.id == cons.id) index = this.atendimento.consumidores.indexOf(c);
        }
        if (index > -1) this.removerConsumidor(index);
      }
      const consDto: ConsumidorDto = {
        id: cons.id != null ? cons.id : 0,
        tipo: cons.tipo,
        denominacao: cons.denominacao,
        cadastro: cons.cadastro
      }
      this.atendimento.consumidores.push(consDto);
    }

    setTimeout(() => {
      this.scrollConsumidor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      });
    }, 100);
  }

  fornecedorSalvo(forn: Fornecedor | null) {
    this.editandoForn = false;
    if (forn != null) {
      if (this.atendimento.fornecedores.length > 0) {
        let index = -1;
        for (let f of this.atendimento.fornecedores) {
          if (f.id == forn.id) index = this.atendimento.fornecedores.indexOf(f);
        }
      }
      const fornDto: FornecedorDto = {
        id: forn.id != null ? forn.id : 0,
        fantasia: forn.fantasia,
        razao: forn.razaoSocial,
        cnpj: forn.cnpj
      }
      this.atendimento.fornecedores.push(fornDto);
    }


    setTimeout(() => {
      this.scrollFornecedor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      });
    }, 100);
  }

  cancelar() {
    this.location.back();
  }

  getInputClass(field: string): string {
    return getInputClass(field, this.form);
  }

}
