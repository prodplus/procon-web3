import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { faPrint } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs';

import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { AtendimentoDto } from 'src/app/models/dtos/atendimento-dto';
import { AtendimentoService } from 'src/app/services/atendimento.service';
import { RelatorioService } from 'src/app/services/relatorio.service';

@Component({
  selector: 'app-lista-atendimentos',
  templateUrl: './lista-atendimentos.component.html',
  styleUrls: ['./lista-atendimentos.component.css']
})
export class ListaAtendimentosComponent implements OnInit, AfterViewInit {
  isLoading = false;
  page: Page<AtendimentoDto> = new Page();
  idAtendimento: number | null = null;
  searchFormCons!: FormGroup;
  searchConsValue: string | null = null;
  searchFormForn!: FormGroup;
  searchFornValue: string | null = null;
  pagina = 1;
  iPrint = faPrint;
  @ViewChild('modal')
  modal!: ModalComponent;
  atendimentosAno = 0;

  constructor(
    private builder: FormBuilder,
    private atendimentoService: AtendimentoService,
    private relService: RelatorioService
  ) { }

  ngOnInit(): void {
    this.searchFormCons = this.builder.group({
      input: [''],
    });
    this.searchFormForn = this.builder.group({
      input: [''],
    });

    this.isLoading = true;
    this.recarregar(this.pagina, this.searchConsValue, this.searchFornValue);
    this.atendimentoService.atendimentosAno().subscribe((v) => (this.atendimentosAno = v));
  }

  ngAfterViewInit(): void {
    this.searchFormCons.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (value && value.length > 0) {
          this.searchConsValue = value;
          this.pagina = 1;
          this.recarregar(this.pagina, this.searchConsValue, this.searchFornValue);
        } else {
          this.searchFornValue = null;
        }
      }
    );
    this.searchFormForn.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (value && value.length > 0) {
          this.searchFornValue = value;
          this.pagina = 1;
          this.recarregar(this.pagina, this.searchConsValue, this.searchFornValue);
        } else {
          this.searchFornValue = null;
        }
      }
    );
  }

  private recarregar(pagina: number, valueC: string | null, valueF: string | null) {
    if (valueC == null && valueF == null) {
      this.atendimentoService.listar(pagina - 1, 20).subscribe({
        next: (p) => (this.page = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => (this.isLoading = false),
      });
    } else if (valueC != null) {
      valueF = null;
      this.atendimentoService.listarConsumidor(pagina - 1, 20, valueC).subscribe({
        next: (p) => (this.page = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => (this.isLoading = false),
      });
    } else if (valueF != null) {
      this.atendimentoService.listarFornecedor(pagina - 1, 20, valueF).subscribe({
        next: (p) => (this.page = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => (this.isLoading = false),
      });
    }
  }

  mudaPagina(pagina: number) {
    this.pagina = pagina;
    this.recarregar(this.pagina, this.searchConsValue, this.searchFornValue);
  }

  chamaModal(resp: { id: number | null; tipo: string }) {
    this.idAtendimento = resp.id;
    this.modal.open('warning', 'Atenção!!',
      `Tem certeza que deseja EXCLUIR o atendimento??`,
      resp.tipo, true);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.atendimentoService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.pagina = 1;
        this.searchConsValue = null;
        this.searchFornValue = null;
        this.recarregar(this.pagina, this.searchConsValue, this.searchFornValue);
        this.modal.open('success', 'Pronto!!', 'Fornecedor excluído!', 'e', false);
      }
    });
  }

  concordou(resp: RespModal) {
    if (resp.confirmou && this.idAtendimento != null)
      this.excluir(this.idAtendimento);
  }

  imprimir(id: number) {
    this.relService.atendimentoIni(id).subscribe({
      next: (x) => window.open(window.URL.createObjectURL(x), '_blank'),
      error: (err) => this.modal.openErro(err),
    });
  }

}
