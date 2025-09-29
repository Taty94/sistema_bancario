import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { Cliente } from '../../models/cliente.model';
import { ClientesService } from '../../services/clientes.service';

@Component({
    selector: 'app-cliente-list',
    templateUrl: './cliente-list.component.html',
    styleUrls: ['./cliente-list.component.css'],
    standalone: false
})
export class ClienteListComponent implements OnInit {
    estadoFiltro: string = '';
    clientesFiltrados: Cliente[] = [];
    @ViewChild('toast', { static: true }) toast?: ToastComponent;
    clientes: Cliente[] = [];
    totalPages = 1;
    currentPage = 0;
    pageSize = 2;
    totalElements = 0;

    modalDetalleAbierto = false;
    clienteSeleccionado: Cliente | null = null;

    modalEliminarAbierto = false;
    clienteAEliminar: Cliente | null = null;
    abrirModalEliminar(cliente: Cliente) {
        this.clienteAEliminar = cliente;
        this.modalEliminarAbierto = true;
    }

    cerrarModalEliminar() {
        this.modalEliminarAbierto = false;
        this.clienteAEliminar = null;
    }

    confirmarEliminar() {
        if (!this.clienteAEliminar?.id) return;
        this.clientesService.deleteCliente(this.clienteAEliminar.id).subscribe({
            next: () => {
                this.toast?.show('Cliente eliminado correctamente', 'success');
                this.cargarClientes(this.currentPage);
                this.cerrarModalEliminar();
            },
            error: (err) => {
                const code = err?.error?.errors?.[0]?.code || 'Error';
                const message = err?.error?.errors?.[0]?.message || err?.error?.detail || 'Ocurrió un error al eliminar el cliente.';
                this.toast?.show(message, 'error', code);
            }
        });
    }

    formularioAbierto = false;
    clienteEditando: Cliente | null = null;

    constructor(private clientesService: ClientesService, private router: Router) { }

    ngOnInit(): void {
        this.cargarClientes(0);
    }

    cargarClientes(page: number) {
        this.clientesService.getClientes(page, this.pageSize).subscribe((data) => {
            this.clientes = data.data;
            this.filtrarPorEstado();
            this.totalPages = data.totalPages || 1;
            this.currentPage = data.pageable?.pageNumber || 0;
            this.totalElements = data.totalElements || this.clientes.length;
        });
    }

    filtrarPorEstado() {
        let estadoParam: boolean | undefined = undefined;
        if (this.estadoFiltro === 'true') {
            estadoParam = true;
        } else if (this.estadoFiltro === 'false') {
            estadoParam = false;
        } // undefined para 'Todos'
        this.cargarClientesPorEstado(this.currentPage, estadoParam);
    }

    cargarClientesPorEstado(page: number, estado?: boolean) {
        this.clientesService.getClientes(page, this.pageSize, estado).subscribe((data) => {
            this.clientes = data.data;
            this.clientesFiltrados = this.clientes;
            this.totalPages = data.totalPages || 1;
            this.currentPage = data.pageable?.pageNumber || 0;
            this.totalElements = data.totalElements || this.clientes.length;
        });
    }


    cambiarPagina(page: number) {
        if (page < 0) {
            page = 0;
        }
        if (page >= this.totalPages) {
            page = this.totalPages - 1;
        }
        if (page !== this.currentPage) {
            this.currentPage = page;
            this.cargarClientes(page);
        }
    }


    abrirModalDetalle(cliente: Cliente) {
        this.clienteSeleccionado = null;
        this.modalDetalleAbierto = true;
        if (cliente.id != null) {
            this.clientesService.getClienteById(cliente.id).subscribe({
                next: (resp) => {
                    this.clienteSeleccionado = resp.data || resp;
                },
                error: (err) => {
                    this.toast?.show('No se pudo cargar el detalle del cliente', 'error');
                    this.clienteSeleccionado = cliente; // fallback a datos de la lista
                }
            });
        } else {
            this.clienteSeleccionado = cliente;
        }
    }

    cerrarModalDetalle() {
        this.modalDetalleAbierto = false;
        this.clienteSeleccionado = null;
    }

    abrirFormulario() {
        this.formularioAbierto = true;
        this.clienteEditando = null;
    }

    editarCliente(cliente: Cliente) {
        this.clienteEditando = { ...cliente };
        this.formularioAbierto = true;
    }

    cerrarFormulario() {
        this.formularioAbierto = false;
        this.clienteEditando = null;
    }

    onClienteGuardado(cliente: Cliente) {
        this.formularioAbierto = false;
        this.clienteEditando = null;
        this.cargarClientes(this.currentPage);
    }

    getLastItemIndex(): number {
        return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
    }
    cambiarPageSize(event: Event) {
        const value = (event.target as HTMLSelectElement).value;
        this.pageSize = Number(value);
        this.cargarClientes(0);
    }
}

