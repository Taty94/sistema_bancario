import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ClienteDetailComponent } from './modules/clientes/components/cliente-detail/cliente-detail.component';
import { ClienteFormComponent } from './modules/clientes/components/cliente-form/cliente-form.component';
import { ClienteListComponent } from './modules/clientes/components/cliente-list/cliente-list.component';
import { CuentaDetailComponent } from './modules/cuentas/components/cuenta-detail/cuenta-detail.component';
import { CuentaFormComponent } from './modules/cuentas/components/cuenta-form/cuenta-form.component';
import { CuentaListComponent } from './modules/cuentas/components/cuenta-list/cuenta-list.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'clientes', component: ClienteListComponent },
    { path: 'clientes/nuevo', component: ClienteFormComponent },
    { path: 'clientes/:id/editar', component: ClienteFormComponent },
    { path: 'clientes/:id', component: ClienteDetailComponent },
    // Rutas para cuentas
    { path: 'cuentas', component: CuentaListComponent },
    { path: 'cuentas/nueva', component: CuentaFormComponent },
    { path: 'cuentas/:id/editar', component: CuentaFormComponent },
    { path: 'cuentas/:id', component: CuentaDetailComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
