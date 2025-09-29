package com.pichincha.dm.crms.banking.domain.models;

import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.GenerateReporte200Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReporteRespuesta  implements GenerateReporte200Response {
    private ReporteEstadoCuenta data;
    private String message;

}
