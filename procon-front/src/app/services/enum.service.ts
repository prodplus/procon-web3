import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const URL = environment.url + '/enums';

@Injectable({providedIn: 'root'})
export class EnumService {
  constructor(private http: HttpClient) { }

  getTiposPessoa(): Observable<string[]> {
    return this.http.get<string[]>(`${URL}/tipos_pessoa`);
  }

  getUfs(): Observable<string[]> {
    return this.http.get<string[]>(`${URL}/ufs`);
  }

  getTiposProcesso(): Observable<string[]> {
    return this.http.get<string[]>(`${URL}/tipos_processo`);
  }

  getSituacoes(): Observable<string[]> {
    return this.http.get<string[]>(`${URL}/situacoes`);
  }

}
