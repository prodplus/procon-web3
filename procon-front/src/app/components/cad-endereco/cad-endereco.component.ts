import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { NgxViacepService } from '@brunoc/ngx-viacep';

import { EnumService } from 'src/app/services/enum.service';
import { getInputClass, getInputUpperClass, getSelectClass } from 'src/app/utils/validation';

@Component({
  selector: 'app-cad-endereco',
  templateUrl: './cad-endereco.component.html',
  styleUrls: ['./cad-endereco.component.css']
})
export class CadEnderecoComponent implements OnInit {
  @Input() form!: FormGroup;
  @ViewChild('inputNro')
  inputNro!: ElementRef<HTMLInputElement>;
  iSearch = faSearch;
  ufs: string[] = [];

  constructor(
    private cepService: NgxViacepService,
    private enumService: EnumService
  ) { }

  ngOnInit(): void {
    this.enumService.getUfs().subscribe((u) => (this.ufs = u));
  }

  buscarEnderecoPorCep() {
    this.cepService.buscarPorCep(this.form.get('cep')?.value).subscribe({
      next: (e) => {
        this.form.get('logradouro')?.setValue(e.logradouro);
        this.form.get('bairro')?.setValue(e.bairro);
        this.form.get('municipio')?.setValue(e.localidade);
        this.form.get('uf')?.setValue(e.uf);
      },
      error: (err) => (console.log(err)),
    });
  }

  buscarCepPorEndereco() {
    this.cepService.buscarPorEndereco(
      this.form.get('uf')?.value,
      this.form.get('municipio')?.value,
      this.form.get('logradouro')?.value
    ).subscribe({
      next: (c) => this.form.get('cep')?.setValue(c[0].cep),
      error: (err) => (console.log(err)),
    });
  }

  getBuscaCepDisable(): boolean {
    if (this.form.get('uf')?.valid && this.form.get('municipio')?.valid &&
        this.form.get('logradouro')?.valid)
      return false;
    else
      return true;
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
