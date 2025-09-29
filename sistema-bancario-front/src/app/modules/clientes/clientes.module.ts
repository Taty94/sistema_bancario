import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { ClienteDetailComponent } from './components/cliente-detail/cliente-detail.component';
import { ClienteFormComponent } from './components/cliente-form/cliente-form.component';
import { ClienteListComponent } from './components/cliente-list/cliente-list.component';

@NgModule({
    declarations: [
        ClienteListComponent,
        ClienteFormComponent,
        ClienteDetailComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        SharedModule
    ],
    exports: [
        ClienteListComponent,
        ClienteFormComponent,
        ClienteDetailComponent
    ]
})
export class ClientesModule { }
