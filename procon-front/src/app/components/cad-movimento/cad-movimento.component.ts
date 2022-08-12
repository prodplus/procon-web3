import { AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faWindowClose } from '@fortawesome/free-solid-svg-icons';
import { Movimento } from 'src/app/models/auxiliares/movimento';
import { EnumService } from 'src/app/services/enum.service';
import { toDateApi } from 'src/app/utils/date-utils';

@Component({
  selector: 'app-cad-movimento',
  templateUrl: './cad-movimento.component.html',
  styleUrls: ['./cad-movimento.component.css']
})
export class CadMovimentoComponent implements OnInit, AfterViewInit {
  @Output() cadMovimento = new EventEmitter<Movimento | null>();
  iWindowClose = faWindowClose;
  situacoes: string[] = [];
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  form!: FormGroup;

  constructor(
    private builder: FormBuilder,
    private enumService: EnumService
  ) { }

  ngOnInit(): void {
    this.enumService.getSituacoes().subscribe((s) => (this.situacoes = s));

    this.form = this.builder.group({
      data: [null, [Validators.required]],
      de: [null, [Validators.required]],
      para: [null, [Validators.required]],
      averbacao: [''],
      auxD: [''],
      auxT: ['']
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  cancelar() {
    this.cadMovimento.emit(null);
  }

  private carregaMovimento(): Movimento {
    let d: string;
    if (this.form.get('auxD')?.value)
      d = toDateApi(new Date(this.form.get('auxD')?.value));
    else d = '';
    return new Movimento(toDateApi(new Date(this.form.get('data')?.value)),
        this.form.get('de')?.value, this.form.get('para')?.value,
        this.form.get('averbacao')?.value, d, this.form.get('auxT')?.value);
  }

  salvar() {
    this.cadMovimento.emit(this.carregaMovimento());
  }

}
