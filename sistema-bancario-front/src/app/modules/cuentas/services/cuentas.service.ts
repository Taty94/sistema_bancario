import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cuenta } from '../models/cuenta.model';

@Injectable({ providedIn: 'root' })
export class CuentasService {
    private apiUrl = 'http://localhost:8082/cuentas';

    constructor(private http: HttpClient) { }

    getCuentas(page: number, size: number, idCliente?: number): Observable<any> {
        let params: any = { page, size };
        if (idCliente !== undefined) params.clienteId = idCliente;
        return this.http.get<any>(this.apiUrl, { params });
    }

    getCuentaByNumeroCuenta(numeroCuenta: string): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${numeroCuenta}`);
    }

    createCuenta(cuenta: Cuenta): Observable<any> {
        // Formatear tipoCuenta: primera letra mayúscula, resto minúsculas
        let tipoCuenta = cuenta.tipo;
        if (tipoCuenta) {
            tipoCuenta = tipoCuenta.charAt(0).toUpperCase() + tipoCuenta.slice(1).toLowerCase();
        }
        return this.http.post<any>(this.apiUrl, {
            data: {
                tipoCuenta,
                saldoInicial: cuenta.saldo,
                idCliente: cuenta.clienteId
            }
        });
    }

    updateCuenta(numeroCuenta: string, cuenta: Cuenta): Observable<any> {
        // Solo enviar tipoCuenta en el formato requerido por el backend
        // Formatear tipoCuenta: primera letra mayúscula, resto minúsculas
        let tipoCuenta = cuenta.tipo;
        if (tipoCuenta) {
            tipoCuenta = tipoCuenta.charAt(0).toUpperCase() + tipoCuenta.slice(1).toLowerCase();
        }
        return this.http.put<any>(`${this.apiUrl}/${numeroCuenta}`, {
            data: {
                tipoCuenta
            }
        });
    }

    deleteCuenta(numeroCuenta: string): Observable<any> {
        return this.http.delete<any>(`${this.apiUrl}/${numeroCuenta}`);
    }
}
