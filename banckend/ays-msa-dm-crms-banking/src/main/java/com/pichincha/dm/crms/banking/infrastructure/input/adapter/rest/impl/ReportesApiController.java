package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.impl;

import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.ReportesApi;
import com.pichincha.dm.crms.banking.application.port.input.ReporteInputPort;
import com.pichincha.dm.crms.banking.domain.models.ReportePDFRespuesta;
import com.pichincha.dm.crms.banking.domain.models.ReporteRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.FormatoReporteValues;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.GenerateReporte200Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class ReportesApiController implements ReportesApi {
    private final ReporteInputPort reporteInputPort;

    @Override
    public ResponseEntity<GenerateReporte200Response> generateReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin, FormatoReporteValues formato) {
        if (formato == FormatoReporteValues.PDF) {
            ReportePDFRespuesta respuesta = (ReportePDFRespuesta) reporteInputPort.generarReporte(
                    clienteId, fechaInicio, fechaFin, formato.getValue());
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            ReporteRespuesta respuesta = (ReporteRespuesta) reporteInputPort.generarReporte(
                    clienteId, fechaInicio, fechaFin, formato.getValue());
            return ResponseEntity.ok(respuesta);
        }
    }
}   
