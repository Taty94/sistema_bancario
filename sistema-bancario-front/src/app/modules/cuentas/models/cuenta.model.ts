export interface Cuenta {
    id?: number;
    numeroCuenta: string;
    tipo: 'AHORROS' | 'CORRIENTE';
    saldo: number;
    estado: boolean;
    clienteId: number;
    fechaCreacion?: string;
    nombreCliente?: string;
}
