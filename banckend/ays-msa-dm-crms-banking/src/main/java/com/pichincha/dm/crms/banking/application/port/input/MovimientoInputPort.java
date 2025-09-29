package com.pichincha.dm.crms.banking.application.port.input;

import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;

public interface MovimientoInputPort {
    MovimientoRespuesta crearMovimiento(Movimiento movimientoData);
    MovimientoListRespuesta listarMovimientos(int page, int size, String numeroCuenta);
}
