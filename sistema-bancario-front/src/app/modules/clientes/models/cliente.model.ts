export interface Cliente {
    id?: number;
    nombre: string;
    apellido: string;
    genero: string;
    edad: number;
    tipoIdentificacion: string;
    identificacion: string;
    callePrincipal: string;
    calleSecundaria: string;
    ciudad: string;
    provincia: string;
    numeroCasa: string;
    contrasena: string;
    telefono: string;
    clienteId?: string;
    estado?: boolean;
}
