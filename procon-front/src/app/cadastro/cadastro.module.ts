import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { CadastroRoutingModule } from './cadastro-routing.module';
import { ComponentsModule } from '../components/components.module';
import { HomeComponent } from './home/home.component';
import { ListaConsumidoresComponent } from './consumidores/lista-consumidores/lista-consumidores.component';
import { CadConsumidoresComponent } from './consumidores/cad-consumidores/cad-consumidores.component';
import { ListaFornecedoresComponent } from './fornecedores/lista-fornecedores/lista-fornecedores.component';
import { CadFornecedoresComponent } from './fornecedores/cad-fornecedores/cad-fornecedores.component';
import { ListaAtendimentosComponent } from './atendimentos/lista-atendimentos/lista-atendimentos.component';
import { CadAtendimentosComponent } from './atendimentos/cad-atendimentos/cad-atendimentos.component';
import { CadProcessosComponent } from './processos/cad-processos/cad-processos.component';
import { ListaProcessosComponent } from './processos/lista-processos/lista-processos.component';

@NgModule({
  declarations: [
    HomeComponent,
    ListaConsumidoresComponent,
    CadConsumidoresComponent,
    ListaFornecedoresComponent,
    CadFornecedoresComponent,
    ListaAtendimentosComponent,
    CadAtendimentosComponent,
    CadProcessosComponent,
    ListaProcessosComponent
  ],
  imports: [
    CommonModule,
    CadastroRoutingModule,
    ComponentsModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskModule,
    FontAwesomeModule,
  ],
  exports: [
    CadConsumidoresComponent,
    CadFornecedoresComponent
  ]
})
export class CadastroModule { }
