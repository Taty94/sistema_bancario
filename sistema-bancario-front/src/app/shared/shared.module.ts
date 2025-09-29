import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { LoaderComponent } from './components/loader/loader.component';
import { ModalComponent } from './components/modal/modal.component';
import { TableComponent } from './components/table/table.component';
import { ToastComponent } from './components/toast/toast.component';

@NgModule({
    declarations: [
        LoaderComponent,
        ModalComponent,
        TableComponent,
        ToastComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        LoaderComponent,
        ModalComponent,
        TableComponent,
        ToastComponent
    ]
})
export class SharedModule { }
