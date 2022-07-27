import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { CadConsumidorResolver } from "../resolvers/cad-consumidor.resolver";
import { CadConsumidoresComponent } from "./consumidores/cad-consumidores/cad-consumidores.component";
import { ListaConsumidoresComponent } from "./consumidores/lista-consumidores/lista-consumidores.component";

import { HomeComponent } from "./home/home.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'consumidores', children: [
      { path: '', component: ListaConsumidoresComponent },
      {
        path: 'novo', children: [
          { path: '', component: CadConsumidoresComponent },
          {
            path: ':id', component: CadConsumidoresComponent,
            resolve: { consumidor: CadConsumidorResolver }
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CadastroRoutingModule { }
