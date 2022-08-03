import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TituloComponent } from './titulo/titulo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaskModule } from 'ngx-mask';
import { GoogleChartsModule } from 'angular-google-charts';
import { NgxViacepModule } from '@brunoc/ngx-viacep';

import { BotaoAtivarComponent } from './botao-ativar/botao-ativar.component';
import { BotaoAtivarTabComponent } from './botao-ativar-tab/botao-ativar-tab.component';
import { BotaoCancelarComponent } from './botao-cancelar/botao-cancelar.component';
import { BotaoEditarTabComponent } from './botao-editar-tab/botao-editar-tab.component';
import { BotaoExcluirTabComponent } from './botao-excluir-tab/botao-excluir-tab.component';
import { BotaoHomeComponent } from './botao-home/botao-home.component';
import { BotaoNovoComponent } from './botao-novo/botao-novo.component';
import { ControleComponent } from './controle/controle.component';
import { ControleAtivosComponent } from './controle-ativos/controle-ativos.component';
import { ModalComponent } from './modal/modal.component';
import { PaginaComponent } from './pagina/pagina.component';
import { PaginadorComponent } from './paginador/paginador.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { TabelaComponent } from './tabela/tabela.component';
import { CadEnderecoComponent } from './cad-endereco/cad-endereco.component';
import { SearchInputComponent } from './search-input/search-input.component';
import { CadFoneComponent } from './cad-fone/cad-fone.component';
import { SelecConsComponent } from './selec-cons/selec-cons.component';
import { SelecFornComponent } from './selec-forn/selec-forn.component';
import { TabConsumidoresComponent } from './tab-consumidores/tab-consumidores.component';
import { TabFornecedoresComponent } from './tab-fornecedores/tab-fornecedores.component';

@NgModule({
  declarations: [
    BotaoAtivarComponent,
    BotaoAtivarTabComponent,
    BotaoCancelarComponent,
    BotaoEditarTabComponent,
    BotaoExcluirTabComponent,
    BotaoHomeComponent,
    BotaoNovoComponent,
    ControleComponent,
    ControleAtivosComponent,
    ModalComponent,
    PaginaComponent,
    PaginadorComponent,
    SpinnerComponent,
    TabelaComponent,
    TituloComponent,
    CadEnderecoComponent,
    SearchInputComponent,
    CadFoneComponent,
    SelecConsComponent,
    SelecFornComponent,
    TabConsumidoresComponent,
    TabFornecedoresComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FontAwesomeModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    NgxMaskModule,
    GoogleChartsModule,
    NgxViacepModule
  ],
  exports: [
    BotaoAtivarTabComponent,
    BotaoCancelarComponent,
    BotaoEditarTabComponent,
    BotaoExcluirTabComponent,
    BotaoHomeComponent,
    ControleComponent,
    ControleAtivosComponent,
    ModalComponent,
    PaginaComponent,
    SpinnerComponent,
    TabelaComponent,
    TituloComponent,
    CadEnderecoComponent,
    SearchInputComponent,
    CadFoneComponent,
    SelecConsComponent,
    SelecFornComponent,
    TabConsumidoresComponent,
    TabFornecedoresComponent
  ]
})
export class ComponentsModule { }
