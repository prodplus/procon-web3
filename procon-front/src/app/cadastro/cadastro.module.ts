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

@NgModule({
  declarations: [
    HomeComponent,
    ListaConsumidoresComponent,
    CadConsumidoresComponent
  ],
  imports: [
    CommonModule,
    CadastroRoutingModule,
    ComponentsModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskModule,
    FontAwesomeModule,
  ]
})
export class CadastroModule { }
