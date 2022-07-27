import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { Consumidor } from '../models/consumidor';
import { ConsumidorService } from '../services/consumidor.service';

@Injectable({ providedIn: 'root' })
export class CadConsumidorResolver implements Resolve<Observable<Consumidor>> {
  constructor(private service: ConsumidorService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<Consumidor> {
    return this.service.buscar(route.params['id']);
  }
}
