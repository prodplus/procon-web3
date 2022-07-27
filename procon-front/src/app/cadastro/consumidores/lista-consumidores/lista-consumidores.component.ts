import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime } from 'rxjs';

import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Page } from 'src/app/models/auxiliares/page';
import { RespModal } from 'src/app/models/auxiliares/resp-modal';
import { ConsumidorDto } from 'src/app/models/dtos/consumidor-dto';
import { ConsumidorService } from 'src/app/services/consumidor.service';

@Component({
  selector: 'app-lista-consumidores',
  templateUrl: './lista-consumidores.component.html',
  styleUrls: ['./lista-consumidores.component.css']
})
export class ListaConsumidoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  consumidores!: Page<ConsumidorDto>;
  idConsumidor: number | null = null;
  searchValue: string | null = null;
  form!: FormGroup;
  pagina = 1;
  @ViewChild('modal')
  modal!: ModalComponent;

  constructor(
    private consumidorService: ConsumidorService,
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
      this.consumidorService.listar(pagina - 1, 20).subscribe({
        next: (p) => (this.consumidores = p),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        },
        complete: () => (this.isLoading = false),
      });
    } else {
      this.consumidorService.listarParametro(pagina - 1, 20, value).subscribe({
        next: (p) => (this.consumidores = p),
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
    this.idConsumidor = resp.id;
    this.modal.open('warning', 'Atenção!!',
      'Tem certeza que deseja EXCLUIR o consumidor??', 'e', true);
  }

  private excluir(id: number) {
    this.isLoading = true;
    this.consumidorService.excluir(id).subscribe({
      error: (err) => {
        this.isLoading = false;
        this.modal.openErro(err);
      },
      complete: () => {
        this.pagina = 1;
        this.searchValue = null;
        this.recarregar(this.pagina, this.searchValue);
        this.modal.open('success', 'Pronto!', 'Consumidor excluído!', 'e', false);
      }
    });
  }

  concordou(resp: RespModal) {
    if (resp.confirmou && this.idConsumidor != null)
      this.excluir(this.idConsumidor);
  }

  getMascaraCadastro(cons: ConsumidorDto): string {
    if (cons.tipo === 'FISICA')
      return '000.000.000-00';
    else return '00.000.000/0000-00';
  }
}
