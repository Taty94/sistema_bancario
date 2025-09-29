// ...existing code...
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Cuenta } from '../../../cuentas/models/cuenta.model';
import { CuentasService } from '../../../cuentas/services/cuentas.service';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { Movimiento } from '../../models/movimiento.model';
import { MovimientosService } from '../../services/movimientos.service';

@Component({
    selector: 'app-movimiento-list',
    templateUrl: './movimiento-list.component.html',
    styleUrls: ['./movimiento-list.component.css'],
    standalone: false
})
export class MovimientoListComponent implements OnInit {
    @ViewChild(ToastComponent) toast?: ToastComponent;
    @Input() numeroCuenta: string = '';
    movimientos: Movimiento[] = [];
    pageSize = 10;
    currentPage = 0;
    totalPages = 1;
    totalElements = 0;
    loading = false;
    cuentas: Cuenta[] = [];

    creandoMovimiento = false;
    movimientoForm = {
        tipoMovimiento: '',
        valor: null,
        descripcion: ''
    };
    crearMovimientoError = '';

    constructor(
        private movimientosService: MovimientosService,
        private cuentasService: CuentasService
    ) { }

    ngOnInit(): void {
        this.cargarCuentas();
    }

    mostrarFormularioMovimiento() {
        this.creandoMovimiento = true;
        this.movimientoForm = {
            tipoMovimiento: '',
            valor: null,
            descripcion: ''
        };
        this.crearMovimientoError = '';
    }

    cancelarCrearMovimiento() {
        this.creandoMovimiento = false;
        this.crearMovimientoError = '';
    }

    guardarMovimiento() {
        if (!this.numeroCuenta || !this.movimientoForm.tipoMovimiento || !this.movimientoForm.valor || !this.movimientoForm.descripcion) {
            this.crearMovimientoError = 'Todos los campos son obligatorios';
            return;
        }
        const payload = {
            data: {
                tipoMovimiento: this.movimientoForm.tipoMovimiento,
                valor: Number(this.movimientoForm.valor),
                descripcion: this.movimientoForm.descripcion,
                numeroCuenta: this.numeroCuenta
            }
        };
        this.movimientosService.crearMovimiento(payload).subscribe({
            next: () => {
                this.creandoMovimiento = false;
                this.cargarMovimientos(0);
                if (this.toast) {
                    this.toast.show('Movimiento creado exitosamente', 'success');
                }
            },
            error: () => {
                this.crearMovimientoError = 'Error al crear el movimiento';
                if (this.toast) {
                    this.toast.show('Error al crear el movimiento', 'error');
                }
            }
        });
    }

    cargarCuentas() {
        this.cuentasService.getCuentas(0, 100).subscribe((data: any) => {
            this.cuentas = data.data || [];
            this.cargarMovimientos(0);
        });
    }

    cargarMovimientos(page: number) {
        this.loading = true;
        this.movimientosService.getMovimientos(page, this.pageSize, this.numeroCuenta || undefined).subscribe({
            next: (data) => {
                this.movimientos = data.data || [];
                this.totalPages = data.totalPages || 1;
                this.currentPage = data.pageable?.pageNumber || 0;
                this.totalElements = data.totalElements || this.movimientos.length;
                this.loading = false;
            },
            error: () => {
                this.movimientos = [];
                this.loading = false;
            }
        });
    }

    cambiarPagina(page: number) {
        if (page < 0) page = 0;
        if (page >= this.totalPages) page = this.totalPages - 1;
        if (page !== this.currentPage) {
            this.currentPage = page;
            this.cargarMovimientos(page);
        }
    }

    onCuentaChange() {
        this.cargarMovimientos(0);
    }
}
