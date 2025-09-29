package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ReporteEstadoCuentaResumenCuenta {
    private BigDecimal saldoInicial;
    private Integer totalMovimientos;
    private String saldoFinal;

}
