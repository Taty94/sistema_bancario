import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MovimientosService {
    private apiUrl = 'http://localhost:8082/movimientos';

    constructor(private http: HttpClient) { }

    getMovimientos(page: number, size: number, numeroCuenta?: string): Observable<any> {
        const params: any = { page, size };
        if (numeroCuenta) params.numeroCuenta = numeroCuenta;
        return this.http.get<any>(this.apiUrl, { params });
    }

    crearMovimiento(payload: any) {
        return this.http.post<any>(this.apiUrl, payload);
    }
}
