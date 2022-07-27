import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime } from 'rxjs';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-lista-fornecedores',
  templateUrl: './lista-fornecedores.component.html',
  styleUrls: ['./lista-fornecedores.component.css']
})
export class ListaFornecedoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  fornecedores: Page<FornecedorDto> = new Page();
  idFornecedor: number | null = null;
  searchValue: string | null = null;
  form!: FormGroup;
  pagina = 1;
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private fornecedorService: FornecedorService,
    private builder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.isLoading = true;

    this.form = this.builder.group({
      input: ['']
    });

    this.recarregar(this.pagina, this.searchValue);
  }

  ngAfterViewInit(): void {
    this.form.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe((value) => {
      if (value && value.length > 1) this.searchValue = value;
      else this.searchValue = null;
      this.pagina = 1;
      this.recarregar(this.pagina, this.searchValue);
    });
  }

  private recarregar(pagina: number, value: string | null) {
    if (value == null) {
      this.fornecedorService.listar(pagina - 1, 20).subscribe({
        next: (p) => (this.fornecedores = p),
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
    this.recarregar(this.pagina, this.searchValue);
  }

  chamaModal(resp: { id: number | null; tipo: string }) {
    this.idFornecedor = resp.id;
    this.modal.open('warning', 'Atenção!!',
      'Tem certeza que deseja EXCLUIR o fornecedor??', 'e', true);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.fornecedorService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.pagina = 1;
        this.searchValue = null;
        this.recarregar(this.pagina, this.searchValue);
        this.modal.open('success', 'Pronto!', 'Fornecedor excluído!', 'e', false);
      }
    });
  }

  concordou(resp: RespModal) {
    if (resp.confirmou && this.idFornecedor != null)
      this.excluir(this.idFornecedor);
  }

}
