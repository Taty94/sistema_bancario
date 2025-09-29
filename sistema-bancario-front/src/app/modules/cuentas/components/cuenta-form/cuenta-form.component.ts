import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../../../clientes/models/cliente.model';
import { ClientesService } from '../../../clientes/services/clientes.service';
import { Cuenta } from '../../models/cuenta.model';
@Component({
    selector: 'app-cuenta-form',
    templateUrl: './cuenta-form.component.html',
    styleUrls: ['./cuenta-form.component.css'],
    standalone: false
})
export class CuentaFormComponent implements OnInit {
    clientes: Cliente[] = [];
    @Input() cuentaEditar: Cuenta | null = null;
    @Output() cancelar = new EventEmitter<void>();
    @Output() guardada = new EventEmitter<Cuenta>();

    cuentaForm!: FormGroup;
    esEdicion = false;

    tiposCuenta = [
        { value: 'Ahorros', label: 'Ahorros' },
        { value: 'Corriente', label: 'Corriente' }
    ];

    constructor(private fb: FormBuilder, private clientesService: ClientesService) { }

    ngOnInit(): void {
        this.esEdicion = !!this.cuentaEditar;
        this.cuentaForm = this.fb.group({
            numeroCuenta: [
                { value: this.cuentaEditar?.numeroCuenta || '', disabled: false },
                this.esEdicion ? Validators.required : []
            ],
            tipo: [this.cuentaEditar?.tipo || '', Validators.required],
            saldo: [{ value: this.cuentaEditar?.saldo ?? '', disabled: false }, Validators.required],
            estado: [this.cuentaEditar ? (this.cuentaEditar.estado ? 'Activa' : 'Inactiva') : 'Activa', Validators.required],
            nombreCliente: [{ value: this.cuentaEditar?.nombreCliente || '', disabled: false }],
            clienteId: [this.cuentaEditar?.clienteId || '', Validators.required]
        });
        if (this.esEdicion) {
            this.cuentaForm.get('numeroCuenta')?.disable();
            this.cuentaForm.get('saldo')?.disable();
            this.cuentaForm.get('nombreCliente')?.disable();
            this.cuentaForm.get('estado')?.disable();
            this.cuentaForm.get('clienteId')?.disable();
        } else {
            // Solo cargar clientes en modo creación
            this.clientesService.getClientes(0, 100).subscribe((data) => {
                this.clientes = data.data || [];
            });
        }
    }

    guardar() {
        console.log('Intentando guardar cuenta', this.cuentaForm.value, 'Valido:', this.cuentaForm.valid);
        if (!this.cuentaForm.valid) return;
        if (this.cuentaEditar) {
            // Edición
            const cuentaActualizada: Cuenta = {
                ...this.cuentaEditar,
                tipo: this.cuentaForm.value.tipo
            };
            this.guardada.emit(cuentaActualizada);
        } else {
            // Creación
            const cuentaNueva: Cuenta = {
                numeroCuenta: this.cuentaForm.value.numeroCuenta,
                tipo: this.cuentaForm.value.tipo,
                saldo: Number(this.cuentaForm.value.saldo),
                estado: this.cuentaForm.value.estado === 'Activa',
                clienteId: Number(this.cuentaForm.value.clienteId),
                nombreCliente: this.cuentaForm.value.nombreCliente
            };
            console.log('Emitir cuenta nueva', cuentaNueva);
            this.guardada.emit(cuentaNueva);
        }
    }
}