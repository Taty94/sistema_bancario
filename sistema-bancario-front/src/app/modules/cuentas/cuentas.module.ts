import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { CuentaDetailComponent } from './components/cuenta-detail/cuenta-detail.component';
import { CuentaFormComponent } from './components/cuenta-form/cuenta-form.component';
import { CuentaListComponent } from './components/cuenta-list/cuenta-list.component';

@NgModule({
    declarations: [
        CuentaListComponent,
        CuentaFormComponent,
        CuentaDetailComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SharedModule
    ],
    exports: [CuentaListComponent]
})
export class CuentasModule { }
