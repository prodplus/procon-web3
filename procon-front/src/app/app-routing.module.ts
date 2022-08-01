import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfiguracoesComponent } from './configuracoes/configuracoes.component';
import { AdminGuard } from './core/auth/admin.guard';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent, canActivate: [AdminGuard] },
  {
    path: 'cadastros',
    loadChildren: () => import('./cadastro/cadastro.module').then((m) => m.CadastroModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
