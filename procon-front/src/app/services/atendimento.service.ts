import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FornecedorNro } from '../models/auxiliares/fornecedor-nro';
import { Page } from '../models/auxiliares/page';
import { AtendimentoDto } from '../models/dtos/atendimento-dto';
import { AtendimentoForm } from '../models/forms/atendimento-form';

const URL = environment.url + '/atendimentos';

@Injectable({providedIn: 'root'})
export class AtendimentoService {
  constructor(private http: HttpClient) { }

  salvar(atendimento: AtendimentoForm): Observable<AtendimentoDto> {
    return this.http.post<AtendimentoDto>(URL, atendimento);
  }

  atualizar(id: number, atendimento: AtendimentoForm): Observable<AtendimentoDto> {
    return this.http.put<AtendimentoDto>(`${URL}/${id}`, atendimento);
  }

  buscar(id: number): Observable<AtendimentoDto> {
    return this.http.get<AtendimentoDto>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<AtendimentoDto>> {
    return this.http.get<Page<AtendimentoDto>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarConsumidor(pagina: number, quant: number, parametro: string):
      Observable<Page<AtendimentoDto>> {
    return this.http.get<Page<AtendimentoDto>>(
      `${URL}/consumidor/${pagina}/${quant}/${parametro}`);
  }

  listarFornecedor(pagina: number, quant: number, parametro: string):
      Observable<Page<AtendimentoDto>> {
    return this.http.get<Page<AtendimentoDto>>(
      `${URL}/fornecedor/${pagina}/${quant}/${parametro}`);
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }

  atendimentosAno(): Observable<number> {
    return this.http.get<number>(`${URL}/atendimentos_ano`);
  }

  ranking(ano: number): Observable<FornecedorNro[]> {
    return this.http.get<FornecedorNro[]>(`${URL}/ranking/${ano}`);
  }
}
