export interface Movimiento {
    id?: number;
    idCuenta?: number;
    numeroCuenta: string;
    nombreCliente?: string;
    fecha?: string;
    tipoMovimiento: 'Débito' | 'Crédito';
    valor: number;
    saldoAnterior?: number;
    saldoPosterior?: number;
    descripcion?: string;
    referencia?: string;
    estado?: boolean;
}
