import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const URL = environment.url + '/relatorios';

@Injectable({providedIn: 'root'})
export class RelatorioService {
  constructor(private http: HttpClient) { }

  atendimentoIni(id: number): Observable<Blob> {
    return this.http.get(`${URL}/atendimento/${id}`, { responseType: 'blob' });
  }

  processoIni(id: number): Observable<Blob> {
    return this.http.get(`${URL}/processo/${id}`, { responseType: 'blob' });
  }
}
