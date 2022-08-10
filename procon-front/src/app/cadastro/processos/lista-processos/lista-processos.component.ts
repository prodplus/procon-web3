import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime, Observable, Subscription, timer } from 'rxjs';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';
import { ProcessoDto } from 'src/app/models/dtos/processo-dto';
import { ProcessoService } from 'src/app/services/processo.service';
import { RelatorioService } from 'src/app/services/relatorio.service';

@Component({
  selector: 'app-lista-processos',
  templateUrl: './lista-processos.component.html',
  styleUrls: ['./lista-processos.component.css']
})
export class ListaProcessosComponent implements OnInit, AfterViewInit, OnDestroy {
  isLoading = false;
  page: Page<ProcessoDto> = new Page();
  idProcesso: number | null = null;
  searchFormAutos!: FormGroup;
  searchAutosValue: string | null = null;
  searchFormCons!: FormGroup;
  searchConsValue: string | null = null;
  searchFormForn!: FormGroup;
  searchFornValue: string | null = null;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal!: ModalComponent;
  recarregador: Observable<number> = timer(0, 15000);
  subscription!: Subscription;

  constructor(
    private builder: FormBuilder,
    private processoService: ProcessoService,
    private route: ActivatedRoute,
    private relService: RelatorioService
  ) { }

  ngOnInit(): void {
    this.searchFormAutos = this.builder.group({
      input: [''],
    });

    this.searchFormCons = this.builder.group({
      input: [''],
    });

    this.searchFormForn = this.builder.group({
      input: [''],
    });

    this.isLoading = true;
    this.recarregar();
  }

  ngAfterViewInit(): void {
    this.searchFormAutos.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (value && value.length > 0)
          this.searchAutosValue = value;
        else
          this.searchAutosValue = null;
        this.recarregar();
      }
    );
    this.searchFormCons.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (value && value.length > 0)
          this.searchAutosValue = value;
        else
          this.searchAutosValue = null;
        this.recarregar();
      }
    );
    this.searchFormForn.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (value && value.length > 0)
          this.searchAutosValue = value;
        else
          this.searchAutosValue = null;
        this.recarregar();
      }
    );
  }

  ngOnDestroy(): void {
  }

  private recarregar() {
    if (this.searchAutosValue) {
      this.processoService.listarPorAutos(
        this.pagina - 1, 20, this.searchAutosValue).subscribe({
          next: (p) => this.page = p,
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
        });
    } else if (this.searchConsValue) {
      this.processoService.listarPorConsumidor(
        this.pagina - 1, 20, this.searchConsValue).subscribe({
          next: (p) => this.page = p,
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
        });
    } else if (this.searchFornValue) {
      this.processoService.listarPorFornecedor(
        this.pagina - 1, 20, this.searchFornValue).subscribe({
          next: (p) => this.page = p,
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
        });
    } else {
      this.processoService.listar(this.pagina - 1, 20).subscribe({
        next: (p) => this.page = p,
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
      })
    }
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar();
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.processoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.isLoading = false;
        this.pagina = 1;
        this.searchAutosValue = null;
        this.searchConsValue = null;
        this.searchFornValue = null;
        this.recarregar();
        this.modal.open('success', 'Feito!', 'Processo excluído!', 'e', false);
      }
    });
  }

  chamaModal(resp: { id: number | null; tipo: string }) {
    if (resp.id != null) {
      this.idProcesso = resp.id;
      this.modal.open('warning', 'Atenção!!',
        'Tem certeza que deseja EXCLUIR definitivamente o processo??',
        'e', true);
    }
  }

  concordou(resp: RespModal) {
    if (this.idProcesso != null)
      if (resp.confirmou) this.excluir(this.idProcesso);
  }

  imprimir(id: number) {
    this.relService.processoIni(id).subscribe({
      next: (x) => window.open(window.URL.createObjectURL(x), '_blank'),
      error: (err) => this.modal.openErro(err),
    });
  }

  getRowClass(processo: ProcessoDto): string {
    switch (processo.situacao) {
      case 'AUTUADO':
        return 'table-primary';
      case 'CONCLUSO':
      case 'DESPACHO':
      case 'AUTUADO':
      case 'BALCAO':
        return 'table-success';
      case 'AUDIENCIA':
        return 'table-warning';
      case 'PRAZO':
      case 'PRAZO_CONSUMIDOR':
      case 'PRAZO_FORNECEDOR':
      case 'AGUARDA_AR_CONS':
      case 'AGUARDA_AR_FORN':
        return 'table-info';
      case 'NOTIFICAR_FORNECEDOR':
      case 'NOTIFICAR_CONSUMIDOR':
      case 'AGUARDA_AR_FORN':
      case 'AGUARDA_AR_CONS':
        return 'table-danger';
      case 'CARGA':
        return 'table-carga';
      case 'ASSESSORIA':
        return 'table-assessoria';
      case 'RESOLVIDO':
      case 'ENCERRADO':
      case 'INSUBSISTENTE':
        return 'table-secondary';
      case 'NAO_RESOLVIDO':
        return 'table-naoresolvido';
      default:
        return 'table-light';
    }
  }

  getExibNomeForn(forn: FornecedorDto): string {
    if (forn.razao) return forn.razao;
    else return forn.fantasia;
  }

}
