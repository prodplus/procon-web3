import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Fornecedor } from '../models/fornecedor';
import { FornecedorService } from '../services/fornecedor.service';

@Injectable({ providedIn: 'root' })
export class CadFornecedorResolver implements Resolve<Observable<Fornecedor>> {
  constructor(private service: FornecedorService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<Fornecedor> {
    return this.service.buscar(route.params['id']);
  }
}
