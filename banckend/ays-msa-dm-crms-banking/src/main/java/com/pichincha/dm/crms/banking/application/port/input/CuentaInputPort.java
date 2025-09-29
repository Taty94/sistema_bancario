package com.pichincha.dm.crms.banking.application.port.input;

import com.pichincha.dm.crms.banking.domain.models.*;

public interface CuentaInputPort {
    CuentaRespuesta crearCuenta(Cuenta cuentaData);
    CuentaRespuesta obtenerCuentaPorNumero(String numeroCuenta);
    CuentaRespuesta actualizarCuenta(String numeroCuenta, CuentaActualizar cuentaActualizar);
    CuentaListRespuesta listarCuentas(Integer page, Integer size, Long clienteId);
    void eliminarCuenta(String numeroCuenta);
}
