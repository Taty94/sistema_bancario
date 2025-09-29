package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ReporteEstadoCuenta {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ReporteEstadoCuentaCliente cliente;
    private ReporteEstadoCuentaResumenGeneral resumenGeneral;
    private List<ReporteEstadoCuentaCuentas> cuentas ;

}
