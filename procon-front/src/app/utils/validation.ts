import { FormGroup } from "@angular/forms";

export function getInputClass(field: string, form: FormGroup): string {
  if (form.get(field)?.valid) return 'form-control is-valid';
  else return 'form-control is-invalid';
}

export function getInputUpperClass(field: string, form: FormGroup): string {
  if (form.get(field)?.valid) return 'form-control is-valid maiusculas';
  else return 'form-control is-invalid maiusculas';
}

export function getSelectClass(field: string, form: FormGroup): string {
  if (form.get(field)?.valid) return 'form-select is-valid';
  else return 'form-select is-invalid';
}
