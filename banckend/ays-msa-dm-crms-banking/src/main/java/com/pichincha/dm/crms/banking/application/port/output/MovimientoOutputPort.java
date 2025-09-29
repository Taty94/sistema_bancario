package com.pichincha.dm.crms.banking.application.port.output;

import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;

public interface MovimientoOutputPort {
    MovimientoRespuesta crearMovimiento(Movimiento movimientoData);
    MovimientoListRespuesta listarMovimientos(int page, int size, String numeroCuenta);
}
