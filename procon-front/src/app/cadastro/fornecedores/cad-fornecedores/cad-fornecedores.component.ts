import { Location } from '@angular/common';
import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime } from 'rxjs';
import { ModalComponent } from 'src/app/components/modal/modal.component';
import { Endereco } from 'src/app/models/auxiliares/endereco';
import { Fornecedor } from 'src/app/models/fornecedor';
import { FornecedorService } from 'src/app/services/fornecedor.service';
import { getInputClass, getInputUpperClass, getSelectClass } from 'src/app/utils/validation';

@Component({
  selector: 'app-cad-fornecedores',
  templateUrl: './cad-fornecedores.component.html',
  styleUrls: ['./cad-fornecedores.component.css']
})
export class CadFornecedoresComponent implements OnInit, AfterViewInit {
  isLoading = false;
  @Input() idExterno: number | null = null;
  idInterno: number | null = null;
  fones: string[] = [];
  @Output() salvo = new EventEmitter<Fornecedor | null>();
  @ViewChild('input')
  input!: ElementRef<HTMLInputElement>;
  @ViewChild('modal')
  modal!: ModalComponent;
  @ViewChild('srollInit')
  scrollInit!: ElementRef<HTMLDivElement>;
  form!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fornecedorService: FornecedorService,
    private builder: FormBuilder,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      fantasia: ['', [Validators.required]],
      razaoSocial: [''],
      cnpj: [''],
      email: ['', [Validators.email]],
      cep: [''],
      logradouro: [''],
      numero: [''],
      complemento: [''],
      bairro: [''],
      municipio: [''],
      uf: [''],
    });

    this.route.paramMap.subscribe((values) => {
      if (this.idExterno != null && this.idExterno != 0) {
        this.isLoading = true;
        this.fornecedorService.buscar(this.idExterno).subscribe({
          next: (f) => (this.carregaFormulario(f)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          },
          complete: () => (this.isLoading = false),
        });
      } else if (values.get('id')) {
        const forn: Fornecedor = this.route.snapshot.data['fornecedor'];
        this.idInterno = forn.id;
        this.carregaFormulario(forn);
      }
    });
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.input.nativeElement.focus();
    }, 100);

    this.form.get('fantasia')?.valueChanges.pipe(debounceTime(300)).subscribe(
      (value) => {
        if (this.idExterno == null && this.idInterno == null) {
          let b = false;
          this.fornecedorService.fornecedorExiste(value).subscribe({
            next: (v) => (b = v),
            complete: () => {
              if (b)
                this.form.get('fantasia')?.setErrors({ fornExiste: true });
              else
                this.form.get('fantasia')?.setErrors(null);
            }
          });
        }
      }
    );

    this.form.get('cnpj')?.valueChanges.subscribe((value) => {
      if (value.length >= 14) {
        this.fornecedorService.cnpjExiste(value).subscribe(
          (v) => {
            if (v) this.form.get('cnpj')?.setErrors({ fornExiste: true });
            else this.form.get('cnpj')?.setErrors(null);
          }
        );
      }
    });
  }

  private carregaFormulario(forn: Fornecedor) {
    this.form.patchValue({
      fantasia: forn.fantasia,
      razaoSocial: forn.razaoSocial,
      cnpj: forn.cnpj,
      email: forn.email,
      cep: forn.endereco?.cep,
      logradouro: forn.endereco?.logradouro,
      numero: forn.endereco?.numero,
      complemento: forn.endereco?.complemento,
      bairro: forn.endereco?.bairro,
      municipio: forn.endereco?.municipio,
      uf: forn.endereco?.uf,
    });
    this.fones = forn.fones;
  }

  private carregaFornecedor(): Fornecedor {
    const idI: number | null = this.idExterno != null && this.idExterno != 0
      ? this.idExterno : this.idExterno == 0 ? null
      : this.idInterno != null
      ? this.idInterno : null;
    return new Fornecedor(idI, this.form.get('fantasia')?.value,
      this.form.get('razaoSocial')?.value, this.form.get('cnpj')?.value,
      this.form.get('email')?.value, new Endereco(this.form.get('cep')?.value,
      this.form.get('logradouro')?.value, this.form.get('numero')?.value,
      this.form.get('complemento')?.value, this.form.get('bairro')?.value,
      this.form.get('municipio')?.value, this.form.get('uf')?.value), this.fones);
  }

  private procedimentoSalvar(forn: Fornecedor) {
    if (this.idExterno != null) this.salvo.emit(forn);
    else this.router.navigate(['/cadastros/fornecedores']);
  }

  cancelar() {
    if (this.idExterno != null) this.salvo.emit(null);
    else this.location.back();
  }

  salvar() {
    this.isLoading = true;
    if (this.idInterno != null) {
      this.fornecedorService.atualizar(this.idInterno, this.carregaFornecedor()).subscribe({
        next: (f) => (this.procedimentoSalvar(f)),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        }
      });
    } else if (this.idExterno != null) {
      if (this.idExterno == 0) {
        this.fornecedorService.salvar(this.carregaFornecedor()).subscribe({
          next: (f) => (this.procedimentoSalvar(f)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          }
        });
      } else {
        this.fornecedorService.atualizar(this.idExterno, this.carregaFornecedor()).subscribe({
          next: (f) => (this.procedimentoSalvar(f)),
          error: (err) => {
            this.isLoading = false;
            this.modal.openErro(err);
          }
        });
      }
    } else {
      this.fornecedorService.salvar(this.carregaFornecedor()).subscribe({
        next: (f) => (this.procedimentoSalvar(f)),
        error: (err) => {
          this.isLoading = false;
          this.modal.openErro(err);
        }
      });
    }
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
