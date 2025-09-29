import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente.model';

@Injectable({
    providedIn: 'root'
})
export class ClientesService {
    private apiUrl = 'http://localhost:8082/clientes';

    constructor(private http: HttpClient) { }

    // Crear cliente
    createCliente(cliente: Cliente): Observable<any> {
        return this.http.post<any>(this.apiUrl, { data: cliente });
    }

    // Obtener cliente por ID
    getClienteById(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    // Actualizar cliente
    updateCliente(id: number, cliente: Partial<Cliente>): Observable<any> {
        return this.http.put<any>(`${this.apiUrl}/${id}`, { data: cliente });
    }

    // Eliminar cliente
    deleteCliente(id: number): Observable<any> {
        return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }

    // Listar clientes con paginación y filtro por estado
    getClientes(page = 0, size = 5, estado?: boolean): Observable<any> {
        let params = new HttpParams()
            .set('page', page)
            .set('size', size);
        if (estado !== undefined) {
            params = params.set('estado', estado);
        }
        return this.http.get<any>(this.apiUrl, { params });
    }
}
