import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReportePdfResponse, ReporteResponse } from '../models/reporte.model';

@Injectable({ providedIn: 'root' })
export class ReportesService {
    private apiUrl = 'http://localhost:8082/reportes';

    constructor(private http: HttpClient) { }

    getReporte(clienteId: string, fechaInicio: string, fechaFin: string, formato: 'json' | 'pdf'): Observable<ReporteResponse | ReportePdfResponse> {
        let params = new HttpParams()
            .set('clienteId', clienteId)
            .set('fechaInicio', fechaInicio)
            .set('fechaFin', fechaFin)
            .set('formato', formato);
        return this.http.get<ReporteResponse | ReportePdfResponse>(this.apiUrl, { params });
    }
}
