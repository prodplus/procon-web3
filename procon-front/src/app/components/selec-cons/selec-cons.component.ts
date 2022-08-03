import { AfterViewInit, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs';
import { Page } from 'src/app/models/auxiliares/page';
import { ConsumidorDto } from 'src/app/models/dtos/consumidor-dto';
import { ConsumidorService } from 'src/app/services/consumidor.service';

@Component({
  selector: 'app-selec-cons',
  templateUrl: './selec-cons.component.html',
  styleUrls: ['./selec-cons.component.css']
})
export class SelecConsComponent implements OnInit, AfterViewInit {
  searchForm!: FormGroup;
  page: Page<ConsumidorDto> = new Page();
  iCheck = faCheck;
  @Output() selecao = new EventEmitter<ConsumidorDto | null>();
  @Output() novo = new EventEmitter<boolean>();

  constructor(
    private builder: FormBuilder,
    private consumidorService: ConsumidorService
  ) { }

  ngOnInit(): void {
    this.searchForm = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchForm.get('input')?.valueChanges.pipe(debounceTime(300)).subscribe({
      next: (value) => {
        if (value.length > 1) {
          this.consumidorService.listarParametro(0, 5, value).subscribe(
            (p) => this.page = p,
          );
        }
      }
    });
  }

  selecionarConsumidor(cons: ConsumidorDto) {
    this.selecao.emit(cons);
  }

  cadastrarNovo() {
    this.novo.emit(true);
  }

  cancelar() {
    this.novo.emit(false);
  }

  mascaraCadastro(tipo: string): string {
    return tipo === 'FISICA' ? '000.000.000-00' : '00.000.000/0000-00';
  }

}
