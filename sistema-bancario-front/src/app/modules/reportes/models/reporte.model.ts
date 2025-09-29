export interface ReporteResponse {
    data: ReporteData;
    message: string;
}

export interface ReporteData {
    fechaInicio: string;
    fechaFin: string;
    cliente: {
        nombre: string;
        clienteId: string;
    };
    resumenGeneral: {
        totalCreditos: number;
        totalDebitos: number;
        saldoFinal: number;
    };
    cuentas: ReporteCuenta[];
}

export interface ReporteCuenta {
    numeroCuenta: string;
    tipoCuenta: string;
    resumenCuenta: {
        saldoInicial: number;
        totalMovimientos: number;
        saldoFinal: string;
    };
    movimientos: ReporteMovimiento[];
}

export interface ReporteMovimiento {
    id: string;
    idCuenta: string | null;
    numeroCuenta: string;
    nombreCliente: string;
    fecha: string;
    tipoMovimiento: string;
    valor: string;
    saldoAnterior: string;
    saldoPosterior: string;
    descripcion: string;
    referencia: string | null;
    estado: string;
}

export interface ReportePdfResponse {
    data: {
        contentType: string;
        filename: string;
        data: string;
    };
    message: string;
}
