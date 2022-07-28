import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxMaskModule } from 'ngx-mask';
import { GoogleChartsModule } from 'angular-google-charts';
import { NgxViacepModule } from '@brunoc/ngx-viacep';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ComponentsModule } from './components/components.module';
import { HeaderComponent } from './header/header.component';
import { ConfiguracoesComponent } from './configuracoes/configuracoes.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    ConfiguracoesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgxMaskModule.forRoot(),
    GoogleChartsModule,
    ComponentsModule,
    NgxViacepModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
