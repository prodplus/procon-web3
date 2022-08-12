import { Location } from '@angular/common';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Movimento } from 'src/app/models/auxiliares/movimento';
import { Consumidor } from 'src/app/models/consumidor';
import { ConsumidorDto } from 'src/app/models/dtos/consumidor-dto';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { ProcessoForm } from 'src/app/models/forms/processo-form';
import { Fornecedor } from 'src/app/models/fornecedor';
import { EnumService } from 'src/app/services/enum.service';
import { ProcessoService } from 'src/app/services/processo.service';
import { UserService } from 'src/app/services/user.service';
import { toDateApi } from 'src/app/utils/date-utils';
import { getInputClass } from 'src/app/utils/validation';

@Component({
  selector: 'app-cad-processos',
  templateUrl: './cad-processos.component.html',
  styleUrls: ['./cad-processos.component.css']
})
export class CadProcessosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  lancandoMov = false;
  selecionandoCons = false;
  selecionandoRepr = false;
  selecionandoForn = false;
  editandoCons = false;
  editandoRepr = false;
  editandoForn = false;
  idConsumidor: number | null = null;
  idRepresentante: number | null = null;
  idFornecedor: number | null = null;
  idProcesso: number | null = null;
  processo!: ProcessoDto;
  movimentacao: Movimento[] = [];
  tipos: string[] = [];
  form!: FormGroup;
  iMinus = faMinus;
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  @ViewChild('scrollCons')
  scrollConsumidor!: ElementRef<HTMLDivElement>;
  @ViewChild('scrollRepr')
  scrollRepresentante!: ElementRef<HTMLDivElement>;
  @ViewChild('scrollForn')
  scrollFornecedor!: ElementRef<HTMLDivElement>;
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private processoService: ProcessoService,
    private enumService: EnumService,
    private builder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.enumService.getTiposProcesso().subscribe((t) => this.tipos = t);

    this.form = this.builder.group({
      data: [toDateApi(new Date()), [Validators.required]],
      autos: ['', [Validators.required]],
      tipo: ['RECLAMACAO', [Validators.required]],
      relato: [''],
    });

    this.route.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.processo = this.route.snapshot.data['processo'];
        this.carregaForm(this.processo);
      } else {
        this.processo = {
          id: 0,
          data: toDateApi(new Date()),
          tipo: '',
          autos: '',
          consumidores: [],
          representantes: [],
          fornecedores: [],
          situacao: '',
          ordem: 0,
          atendente: {
            id: 0,
            nome: '',
            email: '',
            perfil: '',
            ativo: false,
          }
        }
      }
    })
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  private carregaForm(proc: ProcessoDto) {
    this.isLoading = true;
    this.idProcesso = proc.id;
    this.form.get('data')?.setValue(proc.data);
    this.form.get('autos')?.setValue(proc.autos);
    this.form.get('tipo')?.setValue(proc.tipo);
    this.processoService.getRelato(proc.id).subscribe({
      next: (r) => (this.form.get('relato')?.setValue(r.relato)),
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.processoService.getMovimentacao(proc.id).subscribe({
          next: (m) => (this.movimentacao = m),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => this.isLoading = false,
        });
      },
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

  private carregaProcesso(): ProcessoForm {
    let idUsuario: number | null = this.userService.getIdUsuario();
    if (idUsuario == null) idUsuario = 0;
    return new ProcessoForm(this.idProcesso, this.form.get('tipo')?.value,
      this.form.get('autos')?.value, toDateApi(new Date(this.form.get('data')?.value)),
      this.transformaCons(this.processo.consumidores),
      this.transformaCons(this.processo.representantes),
      this.transformaForn(this.processo.fornecedores),
      this.form.get('relato')?.value, idUsuario);
  }

  salvar() {
    this.isLoading = false;
    if (this.idProcesso == null) {
      this.processoService.salvar(this.carregaProcesso()).subscribe({
        next: (p) => (this.processo = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => {
          if (this.movimentacao.length) {
            this.processoService.setMovimentacao(this.processo.id, this.movimentacao).
            subscribe({
              complete: () => {
                this.isLoading = false;
                this.router.navigate(['/cadastros/processos']);
              }
            });
          } else {
            this.isLoading = false;
            this.router.navigate(['/cadastros/processos']);
          }
        }
      });
    } else if (this.idProcesso != null) {
      this.processoService.atualizar(this.idProcesso, this.carregaProcesso()).
        subscribe({
          next: (p) => (this.processo = p),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => {
            if (this.movimentacao.length) {
              this.processoService.setMovimentacao(this.processo.id, this.movimentacao).
              subscribe({
                complete: () => {
                  this.isLoading = false;
                  this.router.navigate(['/cadastros/processos']);
                }
              });
            } else {
              this.isLoading = false;
              this.router.navigate(['/cadastros/processos']);
            }
          }
      });
    }
  }

  selecionandoCEvent(e: boolean) {
    this.selecionandoCons = e;
  }

  selecionandoREvent(e: boolean) {
    this.selecionandoRepr = e;
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

  representanteSelecionado(repr: ConsumidorDto | null) {
    this.selecionandoRepr = false;
    if (repr != null) this.idRepresentante = repr.id;
    else this.idRepresentante = null;
    this.editandoRepr = true;
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

  novoRepresentante(e: boolean) {
    this.selecionandoRepr = false;
    if (e) {
      this.idRepresentante = null;
      this.editandoRepr = true;
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
    this.processo.consumidores.splice(i, 1);
  }

  removerRepresentante(i: number) {
    this.processo.representantes.splice(i, 1);
  }

  removerFornecedor(i: number) {
    this.processo.fornecedores.splice(i, 1);
  }

  consumidorSalvo(cons: Consumidor | null) {
    this.editandoCons = false;
    if (cons != null) {
      if (this.processo.consumidores.length > 0) {
        let index = -1;
        for (let c of this.processo.consumidores) {
          if (c.id == cons.id) index = this.processo.consumidores.indexOf(c);
        }
        if (index != -1) this.removerConsumidor(index);
      }
      const consDto: ConsumidorDto = {
        id: cons.id != null ? cons.id : 0,
        tipo: cons.tipo,
        denominacao: cons.denominacao,
        cadastro: cons.cadastro,
      }
      this.processo.consumidores.push(consDto);
    }

    setTimeout(() => {
      this.scrollConsumidor.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      });
    }, 100);
  }

  representanteSalvo(repr: Consumidor | null) {
    this.editandoRepr = false;
    if (repr != null) {
      if (this.processo.representantes.length > 0) {
        let index = -1;
        for (let r of this.processo.representantes) {
          if (r.id == repr.id) index = this.processo.representantes.indexOf(r);
        }
        if (index != -1) this.removerRepresentante(index);
      }
      const reprDto: ConsumidorDto = {
        id: repr.id != null ? repr.id : 0,
        tipo: repr.tipo,
        denominacao: repr.denominacao,
        cadastro: repr.cadastro,
      }
      this.processo.representantes.push(reprDto);
    }

    setTimeout(() => {
      this.scrollRepresentante.nativeElement.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'center'
      });
    }, 100);
  }

  fornecedorSalvo(forn: Fornecedor | null) {
    this.editandoForn = false;
    if (forn != null) {
      if (this.processo.fornecedores.length > 0) {
        let index = -1;
        for (let f of this.processo.fornecedores) {
          if (f.id == forn.id) index = this.processo.fornecedores.indexOf(f);
        }
      }
      const fornDto: FornecedorDto = {
        id: forn.id != null ? forn.id : 0,
        fantasia: forn.fantasia,
        razao: forn.razaoSocial,
        cnpj: forn.cnpj
      }
      this.processo.fornecedores.push(fornDto);
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

  registrarMov(mov: Movimento | null) {
    if (mov != null) this.movimentacao.unshift(mov);
    this.lancandoMov = false;
  }

}
