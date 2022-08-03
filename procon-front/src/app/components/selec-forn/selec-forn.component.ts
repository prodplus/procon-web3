import { AfterViewInit, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { debounceTime } from 'rxjs';
import { Page } from 'src/app/models/auxiliares/page';
import { FornecedorDto } from 'src/app/models/dtos/fornecedor-dto';
import { FornecedorService } from 'src/app/services/fornecedor.service';

@Component({
  selector: 'app-selec-forn',
  templateUrl: './selec-forn.component.html',
  styleUrls: ['./selec-forn.component.css']
})
export class SelecFornComponent implements OnInit, AfterViewInit {
  searchForm!: FormGroup;
  page: Page<FornecedorDto> = new Page();
  iCheck = faCheck;
  @Output() selecao = new EventEmitter<FornecedorDto | null>();
  @Output() novo = new EventEmitter<boolean>();

  constructor(
    private builder: FormBuilder,
    private fornecedorService: FornecedorService
  ) { }

  ngOnInit(): void {
    this.searchForm = this.builder.group({
      input: [''],
    });
  }

  ngAfterViewInit(): void {
    this.searchForm.get('input')?.valueChanges.pipe(debounceTime(300))
      .subscribe((value) => {
        if (value && value.length > 1) {
          this.fornecedorService.listarP(0, 5, value).subscribe((p) => (this.page = p));
        } else {
          this.page = new Page<FornecedorDto>();
        }
      });
  }

  selecionarFornecedor(forn: FornecedorDto) {
    this.selecao.emit(forn);
  }

  cadastroNovo() {
    this.novo.emit(true);
  }

  cancelar() {
    this.novo.emit(false);
  }

}
