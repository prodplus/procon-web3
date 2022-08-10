import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FornecedorNro } from '../models/auxiliares/fornecedor-nro';
import { Movimento } from '../models/auxiliares/movimento';
import { Page } from '../models/auxiliares/page';
import { ProcDesc } from '../models/auxiliares/prod-desc';
import { RelatoId } from '../models/auxiliares/relato-id';
import { LogDto } from '../models/dtos/log-dto';
import { ProcessoDto } from '../models/dtos/processo-dto';
import { ProcessoForm } from '../models/forms/processo-form';

const URL = environment.url + '/processos';

@Injectable({providedIn: 'root'})
export class ProcessoService {
  constructor(private http: HttpClient) { }

  salvar(processo: ProcessoForm): Observable<ProcessoDto> {
    return this.http.post<ProcessoDto>(URL, processo);
  }

  atualizar(id: number, processo: ProcessoForm): Observable<ProcessoDto> {
    return this.http.put<ProcessoDto>(`${URL}/${id}`, processo);
  }

  getRelato(id: number): Observable<RelatoId> {
    return this.http.get<RelatoId>(`${URL}/relato/${id}`);
  }

  setMovimentacao(id: number, movimentos: Movimento[]): Observable<Movimento[]> {
    return this.http.put<Movimento[]>(`${URL}/movimentos/${id}`, movimentos);
  }

  getMovimentacao(id: number): Observable<Movimento[]> {
    return this.http.get<Movimento[]>(`${URL}/movimentos/${id}`);
  }

  bucar(id: number): Observable<ProcessoDto> {
    return this.http.get<ProcessoDto>(`${URL}/${id}`);
  }

  listar(pagina: number, quant: number): Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(`${URL}/listar/${pagina}/${quant}`);
  }

  listarPorAutos(pagina: number, quant: number, autos: string):
      Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/por_autos/${pagina}/${quant}/${autos}`);
  }

  listarPorConsumidor(pagina: number, quant: number, parametro: string):
      Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/por_consumidor/${pagina}/${quant}/${parametro}`);
  }

  listarPorConsumidorId(idConsumidor: number): Observable<ProcessoDto[]> {
    return this.http.get<ProcessoDto[]>(`${URL}/por_consumidor/${idConsumidor}`);
  }

  listarPorFornecedor(pagina: number, quant: number, parametro: string):
      Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/por_fornecedor/${pagina}/${quant}/${parametro}`);
  }

  listarPorFornecedorId(pagina: number, quant: number, idFornecedor: number):
      Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/por_fornecedor_i/${pagina}/${quant}/${idFornecedor}`);
  }

  listarPorSituacao(pagina: number, quant: number, situacao: string):
      Observable<Page<ProcessoDto>> {
    return this.http.get<Page<ProcessoDto>>(
      `${URL}/por_situacao/${pagina}/${quant}/${situacao}`);
  }

  listarPorFornecedorSituacao(idFornecedor: number, situacao: string):
      Observable<ProcessoDto[]> {
    return this.http.get<ProcessoDto[]>(
      `${URL}/por_fornecedor_situacao/${idFornecedor}/${situacao}`);
  }

  listarPorFornecedorSituacaoDesc(idFornecedor: number, situacao: string):
      Observable<ProcDesc[]> {
    return this.http.get<ProcDesc[]>(
      `${URL}/por_fornecedor_desc/${idFornecedor}/${situacao}`);
  }

  listarPorSituacaoI(situacao: string): Observable<ProcessoDto[]> {
    return this.http.get<ProcessoDto[]>(`${URL}/por_situacao/${situacao}`);
  }

  listarPorSituacaoE(situacao: string, inicio: string, fim: string):
      Observable<ProcessoDto[]> {
    return this.http.get<ProcessoDto[]>(
      `${URL}/por_situacao/${situacao}/${inicio}/${fim}`);
  }

  excluir(id: number) {
    return this.http.delete(`${URL}/${id}`);
  }

  movimentacaoDia(dia: string): Observable<LogDto[]> {
    return this.http.get<LogDto[]>(`${URL}/movimentacao_dia/${dia}`);
  }

  ranking(ano: number): Observable<FornecedorNro[]> {
    return this.http.get<FornecedorNro[]>(`${URL}/ranking/${ano}`);
  }
}
