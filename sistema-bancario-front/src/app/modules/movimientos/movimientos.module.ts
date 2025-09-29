import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { MovimientoListComponent } from './components/movimiento-list/movimiento-list.component';

@NgModule({
    declarations: [MovimientoListComponent],
    imports: [CommonModule, FormsModule, SharedModule],
    exports: [MovimientoListComponent]
})
export class MovimientosModule { }
