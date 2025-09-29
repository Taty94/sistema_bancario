package com.pichincha.dm.crms.banking.application.service;

import com.pichincha.dm.crms.banking.application.port.input.ReporteInputPort;
import com.pichincha.dm.crms.banking.application.port.output.ReporteOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReporteService implements ReporteInputPort {
    private final ReporteOutputPort reporteOutputPort;

    @Override
    public <T> T generarReporte(Long idPersona, LocalDate fechaInicio, LocalDate fechaFin, String formato) {
        return reporteOutputPort.generarReporte(idPersona, fechaInicio, fechaFin, formato);
    }
}
