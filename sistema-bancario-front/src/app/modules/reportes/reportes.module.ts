import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReporteListComponent } from './components/reporte-list/reporte-list.component';

@NgModule({
    declarations: [ReporteListComponent],
    imports: [CommonModule, FormsModule],
    exports: [ReporteListComponent]
})
export class ReportesModule { }
