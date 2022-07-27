import { Location } from '@angular/common';
import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Endereco } from 'src/app/models/auxiliares/endereco';
import { Consumidor } from 'src/app/models/consumidor';
import { ConsumidorService } from 'src/app/services/consumidor.service';
import { EnumService } from 'src/app/services/enum.service';
import { getInputClass, getInputUpperClass, getSelectClass } from 'src/app/utils/validation';

@Component({
  selector: 'app-cad-consumidores',
  templateUrl: './cad-consumidores.component.html',
  styleUrls: ['./cad-consumidores.component.css']
})
export class CadConsumidoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  tipos: string[] = [];
  @Input() idExterno: number | null = null;
  idInterno: number | null = null;
  fones: string[] = [];
  @Output() salvo = new EventEmitter<Consumidor | null>();
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  @ViewChild('modal')
  modal!: ModalComponent;
  @ViewChild('scrollInit')
  scrollInit!: ElementRef<HTMLDivElement>;
  form!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private consumidorService: ConsumidorService,
    private builder: FormBuilder,
    private router: Router,
    private enumService: EnumService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.enumService.getTiposPessoa().subscribe((t) => (this.tipos = t));

    this.form = this.builder.group({
      tipo: ['FISICA', [Validators.required]],
      denominacao: ['', [Validators.required]],
      cadastro: ['', [Validators.required]],
      nascimento: [''],
      email: ['', [Validators.email]],
      cep: ['', [Validators.required]],
      logradouro: ['', [Validators.required]],
      numero: ['', [Validators.required, Validators.maxLength(30)]],
      complemento: ['', [Validators.maxLength(50)]],
      bairro: [''],
      municipio: ['', [Validators.required]],
      uf: [null, [Validators.required]],
    });

    this.route.paramMap.subscribe((values) => {
      if (this.idExterno != null && this.idExterno != 0) {
        this.isLoading = true;
        this.consumidorService.buscar(this.idExterno).subscribe({
          next: (c) => (this.carregaFormulario(c)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
        });
      } else if (values.get('id')) {
        const cons: Consumidor = this.route.snapshot.data['consumidor'];
        this.idInterno = cons.id;
        this.carregaFormulario(cons);
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);
  }

  private carregaFormulario(cons: Consumidor) {
    this.form.patchValue({
      tipo: cons.tipo,
      denominacao: cons.denominacao,
      cadastro: cons.cadastro,
      nascimento: cons.nascimento,
      email: cons.email,
      cep: cons.endereco?.cep,
      logradouro: cons.endereco?.logradouro,
      numero: cons.endereco?.numero,
      complemento: cons.endereco?.complemento,
      bairro: cons.endereco?.bairro,
      municipio: cons.endereco?.municipio,
      uf: cons.endereco?.uf,
    });
    this.fones = cons.fones;
  }

  private carregaConsumidor(): Consumidor {
    const idI: number | null = this.idExterno != null && this.idExterno != 0
      ? this.idExterno : this.idExterno == 0 ? null
      : this.idInterno != null
      ? this.idInterno : null;
    return new Consumidor(idI, this.form.get('tipo')?.value,
        this.form.get('denominacao')?.value.toUpperCase(),
        this.form.get('cadastro')?.value, this.form.get('nascimento')?.value,
        this.form.get('email')?.value, new Endereco(this.form.get('cep')?.value,
        this.form.get('logradouro')?.value.toUpperCase(), this.form.get('numero')?.value,
        this.form.get('complemento')?.value, this.form.get('bairro')?.value,
        this.form.get('municipio')?.value, this.form.get('uf')?.value), this.fones);
  }

  private procedimentoSalvar(cons: Consumidor) {
    if (this.idExterno != null) this.salvo.emit(cons);
    else this.router.navigate(['/cadastros/consumidores']);
  }

  cancelar() {
    if (this.idExterno != null) this.salvo.emit(null);
    else this.location.back();
  }

  salvar() {
    this.isLoading = true;
    if (this.idInterno != null) {
      this.consumidorService.atualizar(this.idInterno, this.carregaConsumidor()).subscribe({
        next: (c) => (this.procedimentoSalvar(c)),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        }
      });
    } else if (this.idExterno != null) {
      if (this.idExterno == 0) {
        this.consumidorService.salvar(this.carregaConsumidor()).subscribe({
          next: (c) => (this.procedimentoSalvar(c)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          }
        });
      } else {
        this.consumidorService.atualizar(this.idExterno, this.carregaConsumidor()).subscribe({
          next: (c) => (this.procedimentoSalvar(c)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          }
        })
      }
    } else {
      this.consumidorService.salvar(this.carregaConsumidor()).subscribe({
        next: (c) => (this.procedimentoSalvar(c)),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        }
      });
    }
  }

  getMascara() {
    if (this.form.get('tipo')?.value === 'JURIDICA')
      return '00.000.000/0000-00';
    else return '000.000.000-00';
  }

  getInputClass(field: string): string {
    return getInputClass(field, this.form);
  }

  getInputUpperClass(field: string): string {
    return getInputUpperClass(field, this.form);
  }

  getSelectClass(field: string): string {
    return getSelectClass(field, this.form);
  }

}
