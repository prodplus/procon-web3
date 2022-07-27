import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';

import { getMascaraFone } from 'src/app/utils/mascaras-utils';

@Component({
  selector: 'app-cad-fone',
  templateUrl: './cad-fone.component.html',
  styleUrls: ['./cad-fone.component.css']
})
export class CadFoneComponent implements OnInit {
  @Input() fones: string[] = [];
  form!: FormGroup;
  iPlus = faPlus;
  iMinus = faMinus;

  constructor(private builder: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.builder.group({
      fone: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

  adicionaFone() {
    this.fones.push(this.form.get('fone')?.value);
    this.form.get('fone')?.reset();
  }

  removeFone(index: number) {
    this.fones.splice(index, 1);
  }

  mascaraFone(fone: string): string {
    return getMascaraFone(fone);
  }

  mascaraFoneInput(): string {
    return getMascaraFone(this.form.get('fone')?.value);
  }

}
