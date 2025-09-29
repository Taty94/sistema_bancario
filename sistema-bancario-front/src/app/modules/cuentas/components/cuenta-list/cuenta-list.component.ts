import { Component, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { Cliente } from '../../../clientes/models/cliente.model';
import { ClientesService } from '../../../clientes/services/clientes.service';
import { Cuenta } from '../../models/cuenta.model';
import { CuentasService } from '../../services/cuentas.service';

@Component({
    selector: 'app-cuenta-list',
    templateUrl: './cuenta-list.component.html',
    styleUrls: ['./cuenta-list.component.css'],
    standalone: false
})
export class CuentaListComponent implements OnInit {
    @ViewChild('toast', { static: true }) toast?: ToastComponent;
    cuentas: Cuenta[] = [];
    cuentasFiltradas: Cuenta[] = [];
    totalPages = 1;
    currentPage = 0;
    pageSize = 5;
    totalElements = 0;
    clientes: Cliente[] = [];
    clienteFiltro: number | '' = '';
    modalDetalleAbierto = false;
    cuentaSeleccionada: Cuenta | null = null;
    modalEliminarAbierto = false;
    cuentaAEliminar: Cuenta | null = null;
    formularioAbierto = false;
    cuentaEditando: Cuenta | null = null;

    constructor(private cuentasService: CuentasService, private clientesService: ClientesService) { }

    ngOnInit(): void {
        this.cargarClientes();
        this.cargarCuentas(0);
    }

    cargarClientes() {
        this.clientesService.getClientes(0, 100).subscribe((data) => {
            console.log(data);
            this.clientes = data.data || [];
        });
    }

    cargarCuentas(page: number) {
        let idCliente: number | null = null;
        if (this.clienteFiltro !== '' && this.clienteFiltro !== null && this.clienteFiltro !== undefined) {
            idCliente = Number(this.clienteFiltro);
        }
        this.cuentasService.getCuentas(page, this.pageSize, idCliente !== null ? idCliente : undefined).subscribe((data) => {
            this.cuentas = (data.data || []).map((c: any) => ({
                ...c,
                tipo: c.tipo ?? c.tipoCuenta ?? '',
                saldo: c.saldo ?? c.saldoActual ?? null,
                nombreCliente: c.nombreCliente ?? c.nombrecliente ?? '',
            }));
            this.cuentasFiltradas = [...this.cuentas];
            this.totalPages = data.totalPages || 1;
            this.currentPage = data.pageable?.pageNumber || 0;
            this.totalElements = data.totalElements || this.cuentas.length;
        });
    }

    onClienteFiltroChange() {
        // Tomar el value del combo y convertirlo a número si corresponde
        if (typeof this.clienteFiltro === 'string' && this.clienteFiltro !== '') {
            const value = Number(this.clienteFiltro);
            this.clienteFiltro = isNaN(value) ? '' : value;
            console.log("EL ILTROOO" + this.clienteFiltro)
        }
        this.cargarCuentas(0);
    }

    cambiarPageSize(event: Event) {
        const value = (event.target as HTMLSelectElement).value;
        this.pageSize = Number(value);
        this.cargarCuentas(0);
    }

    cambiarPagina(page: number) {
        if (page < 0) page = 0;
        if (page >= this.totalPages) page = this.totalPages - 1;
        if (page !== this.currentPage) {
            this.currentPage = page;
            this.cargarCuentas(page);
        }
    }

    abrirModalDetalle(cuenta: Cuenta) {
        this.cuentaSeleccionada = null;
        this.modalDetalleAbierto = true;
        if (cuenta.numeroCuenta) {
            this.cuentasService.getCuentaByNumeroCuenta(cuenta.numeroCuenta).subscribe({
                next: (resp) => {
                    const c = resp.data || resp;
                    this.cuentaSeleccionada = {
                        ...c,
                        tipo: c.tipo ?? c.tipoCuenta ?? '',
                        saldo: c.saldo ?? c.saldoActual ?? null,
                        nombreCliente: c.nombreCliente ?? c.nombrecliente ?? '',
                        fechaCreacion: c.fechaCreacion ?? c.fecha_creacion ?? '',
                    };
                },
                error: () => {
                    this.toast?.show('No se pudo cargar el detalle de la cuenta', 'error');
                    this.cuentaSeleccionada = cuenta;
                }
            });
        } else {
            this.cuentaSeleccionada = cuenta;
        }
    }

    cerrarModalDetalle() {
        this.modalDetalleAbierto = false;
        this.cuentaSeleccionada = null;
    }

    abrirModalEliminar(cuenta: Cuenta) {
        this.cuentaAEliminar = cuenta;
        this.modalEliminarAbierto = true;
    }

    cerrarModalEliminar() {
        this.modalEliminarAbierto = false;
        this.cuentaAEliminar = null;
    }

    confirmarEliminar() {
        if (!this.cuentaAEliminar?.numeroCuenta) return;
        this.cuentasService.deleteCuenta(this.cuentaAEliminar.numeroCuenta).subscribe({
            next: () => {
                this.toast?.show('Cuenta eliminada correctamente', 'success');
                this.cargarCuentas(this.currentPage);
                this.cerrarModalEliminar();
            },
            error: () => {
                this.toast?.show('Ocurrió un error al eliminar la cuenta.', 'error');
            }
        });
    }

    abrirFormulario() {
        this.formularioAbierto = true;
        this.cuentaEditando = null;
    }

    editarCuenta(cuenta: Cuenta) {
        this.cuentaEditando = { ...cuenta };
        this.formularioAbierto = true;
    }

    cerrarFormulario() {
        this.formularioAbierto = false;
        this.cuentaEditando = null;
    }

    onCuentaGuardada(cuenta: Cuenta) {
        if (this.cuentaEditando) {
            // Edición
            if (!cuenta.numeroCuenta) {
                this.toast?.show('No se pudo identificar la cuenta a editar.', 'error');
                return;
            }
            this.toast?.show('Guardando cambios...', 'info');
            this.cuentasService.updateCuenta(cuenta.numeroCuenta, cuenta).subscribe({
                next: () => {
                    this.toast?.show('Cuenta actualizada correctamente', 'success');
                    this.formularioAbierto = false;
                    this.cuentaEditando = null;
                    this.cargarCuentas(this.currentPage);
                },
                error: () => {
                    this.toast?.show('Ocurrió un error al actualizar la cuenta.', 'error');
                }
            });
        } else {
            // Creación
            this.toast?.show('Guardando nueva cuenta...', 'info');
            this.cuentasService.createCuenta(cuenta).subscribe({
                next: () => {
                    this.toast?.show('Cuenta creada correctamente', 'success');
                    this.formularioAbierto = false;
                    this.cuentaEditando = null;
                    this.cargarCuentas(this.currentPage);
                },
                error: () => {
                    this.toast?.show('Ocurrió un error al crear la cuenta.', 'error');
                }
            });
        }
    }

    getLastItemIndex(): number {
        return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
    }
}
