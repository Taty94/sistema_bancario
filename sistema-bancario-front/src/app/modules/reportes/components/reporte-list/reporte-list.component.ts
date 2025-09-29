import { Component, OnInit } from '@angular/core';
import { saveAs } from 'file-saver';
import { Cliente } from '../../../clientes/models/cliente.model';
import { ClientesService } from '../../../clientes/services/clientes.service';
import { ReporteData, ReportePdfResponse } from '../../models/reporte.model';
import { ReportesService } from '../../services/reportes.service';

@Component({
    selector: 'app-reporte-list',
    templateUrl: './reporte-list.component.html',
    styleUrls: ['./reporte-list.component.css'],
    standalone: false
})
export class ReporteListComponent implements OnInit {
    clientes: Cliente[] = [];
    clienteId: string = '';
    fechaInicio: string = '';
    fechaFin: string = '';
    formato: 'json' | 'pdf' = 'json';
    loading = false;
    error = '';
    reporte: ReporteData | null = null;
    mensaje: string = '';

    constructor(
        private reportesService: ReportesService,
        private clientesService: ClientesService
    ) { }

    ngOnInit(): void {
        this.clientesService.getClientes(0, 100).subscribe(resp => {
            console.log(resp.   data);
            this.clientes = resp.data || [];
        });
    }

    buscarReporte() {
        this.error = '';
        this.mensaje = '';
        this.reporte = null;
        if (!this.clienteId || !this.fechaInicio || !this.fechaFin) {
            this.error = 'Debe seleccionar cliente, fecha inicio y fecha fin';
            return;
        }
        this.loading = true;
        this.reportesService.getReporte(this.clienteId, this.fechaInicio, this.fechaFin, this.formato).subscribe({
            next: (resp: any) => {
                this.loading = false;
                if (this.formato === 'json') {
                    this.reporte = resp.data;
                    this.mensaje = resp.message;
                } else if (this.formato === 'pdf') {
                    const pdfResp = resp as ReportePdfResponse;
                    const byteCharacters = atob(pdfResp.data.data);
                    const byteNumbers = new Array(byteCharacters.length);
                    for (let i = 0; i < byteCharacters.length; i++) {
                        byteNumbers[i] = byteCharacters.charCodeAt(i);
                    }
                    const byteArray = new Uint8Array(byteNumbers);
                    const blob = new Blob([byteArray], { type: pdfResp.data.contentType });
                    saveAs(blob, pdfResp.data.filename);
                    this.mensaje = resp.message;
                }
            },
            error: () => {
                this.loading = false;
                this.error = 'Error al obtener el reporte';
            }
        });
    }
}
