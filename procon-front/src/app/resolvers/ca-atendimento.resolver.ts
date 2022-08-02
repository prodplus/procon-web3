import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AtendimentoDto } from '../models/dtos/atendimento-dto';
import { AtendimentoService } from '../services/atendimento.service';

@Injectable({ providedIn: 'root' })
export class CadAtendimentoResolver implements Resolve<Observable<AtendimentoDto>> {
  constructor(private service: AtendimentoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<AtendimentoDto> {
    return this.service.buscar(route.params['id']);
  }
}
