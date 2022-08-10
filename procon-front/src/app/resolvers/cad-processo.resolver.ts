import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { ProcessoDto } from '../models/dtos/processo-dto';
import { ProcessoService } from '../services/processo.service';

@Injectable({ providedIn: 'root' })
export class CadProcessoResolver implements Resolve<Observable<ProcessoDto>> {
  constructor(private service: ProcessoService) { }

  resolve(route: ActivatedRouteSnapshot):  Observable<ProcessoDto >{
    return this.service.bucar(route.params['id']);
  }
}
