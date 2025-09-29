package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCuentaClienteMovimientosDTO {
    private CuentaEntidad cuenta;
    private ClienteEntidad cliente;
    private List<MovimientoEntidad> movimientos;
}
