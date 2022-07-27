import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { CadConsumidorResolver } from "../resolvers/cad-consumidor.resolver";
import { CadFornecedorResolver } from "../resolvers/cad-fornecedor.resolver";
import { CadConsumidoresComponent } from "./consumidores/cad-consumidores/cad-consumidores.component";
import { ListaConsumidoresComponent } from "./consumidores/lista-consumidores/lista-consumidores.component";
import { CadFornecedoresComponent } from "./fornecedores/cad-fornecedores/cad-fornecedores.component";
import { ListaFornecedoresComponent } from "./fornecedores/lista-fornecedores/lista-fornecedores.component";
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
  },
  {
    path: 'fornecedores', children: [
      { path: '', component: ListaFornecedoresComponent },
      {
        path: 'novo', children: [
          { path: '', component: CadFornecedoresComponent },
          {
            path: ':id', component: CadFornecedoresComponent,
            resolve: { fornecedor: CadFornecedorResolver }
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
