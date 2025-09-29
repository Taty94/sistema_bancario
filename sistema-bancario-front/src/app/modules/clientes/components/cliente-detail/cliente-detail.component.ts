import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Cliente } from '../../models/cliente.model';
import { ClientesService } from '../../services/clientes.service';

@Component({
    selector: 'app-cliente-detail',
    templateUrl: './cliente-detail.component.html',
    styleUrls: ['./cliente-detail.component.css'],
    standalone: false // Add this line
})
export class ClienteDetailComponent implements OnInit {
    clienteId: string | null = null;
    cliente: Cliente | null = null;
    loading = true;
    error: string = '';

    constructor(private route: ActivatedRoute, private clientesService: ClientesService) { }

    ngOnInit(): void {
        this.clienteId = this.route.snapshot.paramMap.get('id');
        if (this.clienteId) {
            this.clientesService.getClienteById(Number(this.clienteId)).subscribe({
                next: (resp) => {
                    debugger;
                    this.cliente = resp.data || resp;
                    this.loading = false;
                },
                error: (err) => {
                    this.error = err?.error?.detail || 'No se pudo cargar el cliente.';
                    this.loading = false;
                }
            });
        } else {
            this.error = 'ID de cliente no válido.';
            this.loading = false;
        }
    }
}
