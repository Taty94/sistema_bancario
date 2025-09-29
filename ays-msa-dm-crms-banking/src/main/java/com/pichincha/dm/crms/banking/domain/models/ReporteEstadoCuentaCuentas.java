package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ReporteEstadoCuentaCuentas {
    private String numeroCuenta;
    private String tipoCuenta;
    private ReporteEstadoCuentaResumenCuenta resumenCuenta;
    private List<Movimiento> movimientos;
}
