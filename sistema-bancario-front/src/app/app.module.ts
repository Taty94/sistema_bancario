import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ClientesModule } from './modules/clientes/clientes.module';
import { CuentasModule } from './modules/cuentas/cuentas.module';
import { MovimientosModule } from './modules/movimientos/movimientos.module';
import { ReportesModule } from './modules/reportes/reportes.module';
import { SharedModule } from './shared/shared.module';

import { HttpClientModule } from '@angular/common/http';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        RouterModule,
        AppRoutingModule,
        ClientesModule,
        CuentasModule,
        MovimientosModule,
        SharedModule,
        ReportesModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
