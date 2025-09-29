import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { Cliente } from '../../models/cliente.model';
import { ClientesService } from '../../services/clientes.service';

@Component({
    selector: 'app-cliente-form',
    templateUrl: './cliente-form.component.html',
    styleUrls: ['./cliente-form.component.css'],
    standalone: false
})
export class ClienteFormComponent implements OnInit, OnChanges {
    @ViewChild('toast', { static: true }) toast?: ToastComponent;
    @Input() clienteEditar: Cliente | null = null;
    @Output() cancelar = new EventEmitter<void>();
    @Output() guardado = new EventEmitter<Cliente>();

    cliente: Cliente = {
        nombre: '',
        apellido: '',
        genero: 'Femenino',
        edad: 0,
        tipoIdentificacion: '',
        identificacion: '',
        callePrincipal: '',
        calleSecundaria: '',
        ciudad: '',
        provincia: '',
        numeroCasa: '',
        contrasena: '',
        telefono: '',
        estado: true
    };

    mensajeError: string = '';

    constructor(private clientesService: ClientesService) { }

    ngOnInit() {
        if (this.clienteEditar) {
            this.cliente = { ...this.clienteEditar };
        }
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['clienteEditar'] && changes['clienteEditar'].currentValue) {
            this.cliente = { ...changes['clienteEditar'].currentValue };
        } else if (changes['clienteEditar'] && !changes['clienteEditar'].currentValue) {
            this.resetForm();
        }
    }

    guardar() {
        this.mensajeError = '';
        if (this.clienteEditar && this.clienteEditar.id) {
            // Editar
            this.clientesService.updateCliente(this.clienteEditar.id, this.cliente).subscribe({
                next: (actualizado) => {
                    this.toast?.show('Cliente actualizado correctamente', 'success');
                    this.guardado.emit(actualizado);
                },
                error: (err) => {
                    const code = err?.error?.errors?.[0]?.code || 'Error';
                    const message = err?.error?.errors?.[0]?.message || err?.error?.detail || 'Ocurrió un error al actualizar el cliente.';
                    this.toast?.show(message, 'error', code);
                    this.mensajeError = message;
                }
            });
        } else {
            // Crear
            this.clientesService.createCliente(this.cliente).subscribe({
                next: (nuevoCliente) => {
                    this.toast?.show('Cliente creado correctamente', 'success');
                    this.guardado.emit(nuevoCliente);
                },
                error: (err) => {
                    const code = err?.error?.errors?.[0]?.code || 'Error';
                    const message = err?.error?.errors?.[0]?.message || err?.error?.detail || 'Ocurrió un error al guardar el cliente.';
                    this.toast?.show(message, 'error', code);
                    this.mensajeError = message;
                }
            });
        }
    }

    onCancelar() {
        this.cancelar.emit();
    }

    private resetForm() {
        this.cliente = {
            nombre: '',
            apellido: '',
            genero: 'Femenino',
            edad: 0,
            tipoIdentificacion: '',
            identificacion: '',
            callePrincipal: '',
            calleSecundaria: '',
            ciudad: '',
            provincia: '',
            numeroCasa: '',
            contrasena: '',
            telefono: '',
            estado: true
        };
    }
}
