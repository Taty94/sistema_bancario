package com.pichincha.dm.crms.banking.application.service;

import com.pichincha.dm.crms.banking.application.port.input.MovimientoInputPort;
import com.pichincha.dm.crms.banking.application.port.output.MovimientoOutputPort;
import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimientoService implements MovimientoInputPort {
    private final MovimientoOutputPort movimientoOutputPort;

    @Override
    public MovimientoRespuesta crearMovimiento(Movimiento movimientoData) {
        return movimientoOutputPort.crearMovimiento(movimientoData);
    }

    @Override
    public MovimientoListRespuesta listarMovimientos(int page, int size, String numeroCuenta) {
        return movimientoOutputPort.listarMovimientos(page, size, numeroCuenta);
    }
}
