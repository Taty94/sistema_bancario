package com.pichincha.dm.crms.banking.application.service;

import com.pichincha.dm.crms.banking.application.port.input.CuentaInputPort;
import com.pichincha.dm.crms.banking.application.port.output.CuentaOutputPort;
import com.pichincha.dm.crms.banking.domain.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuentaService implements CuentaInputPort {
    private final CuentaOutputPort cuentaOutputPort;

    @Override
    public CuentaRespuesta crearCuenta(Cuenta cuentaData) {
        return cuentaOutputPort.crearCuenta(cuentaData);
    }

    @Override
    public CuentaRespuesta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaOutputPort.obtenerCuentaPorNumero(numeroCuenta);
    }

    @Override
    public CuentaRespuesta actualizarCuenta(String numeroCuenta, CuentaActualizar cuentaActualizar) {
        return cuentaOutputPort.actualizarCuenta(numeroCuenta, cuentaActualizar);
    }

    @Override
    public CuentaListRespuesta listarCuentas(Integer page, Integer size, Long clienteId) {
        return cuentaOutputPort.listarCuentas(page, size, clienteId);
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        cuentaOutputPort.eliminarCuenta(numeroCuenta);
    }
}
