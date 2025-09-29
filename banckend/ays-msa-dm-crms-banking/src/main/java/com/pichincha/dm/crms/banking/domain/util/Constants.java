package com.pichincha.dm.crms.banking.domain.util;

public class Constants {
    Constants() {
        throw new IllegalStateException("Constant class");
    }

    public static final String CODIGO_ERROR_DESCRIPCION_GENERICA = "Problems to process your transaction";
    public static final String EXCEPCION_DEFECTO = "defaultException";
    public static final String NO_ENCONTRADO_EXCEPCION = "notFoundException";
    public static final String MALA_PETICION_EXCEPCION = "badRequestException";
    public static final String CODIGO_ERROR_GENERICO = "500";
    public static final String CLIENTE_CREADO_EXITOSO = "Cliente creado exitósamente";
    public static final String CLIENTE_ENCONTRADO_EXITOSO = "Cliente encontrado";
    public static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con el id:";
    public static final String CLIENTE_ACTUALIZADO_EXITOSO = "Cliente actualizado";
    public static final String CLIENTE_YA_ELIMINADO = "El cliente ya ha sido deshabilitado";
    public static final String CUENTA_CREADA_EXITOSO = "Cuenta creada exitósamente";
    public static final String CUENTA_ENCONTRADA_EXITOSO = "Cuenta encontrada";
    public static final String CUENTA_NO_ENCONTRADA_ID = "Cuenta no encontrada con el id:";
    public static final String CUENTA_NO_ENCONTRADA_NUMERO = "Cuenta no encontrada con el número de cuenta:";
    public static final String CUENTA_ACTUALIZADA_EXITOSO = "Cuenta actualizada";
    public static final String CUENTA_YA_ELIMINADA = "La cuenta ya ha sido desactivada";
    public static final String MOVIMIENTO_CREADO_EXITOSAMENTE = "Movimiento creado exitósamente";
    public static final String VALOR_MOVIMIENTO_INVALIDO = "El valor del movimiento debe ser positivo.";
    public static final String TiPO_MOVIMIENTO_NO_SOPORTADO = "Tipo de movimiento no soportado: ";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente para realizar el débito.";
}
