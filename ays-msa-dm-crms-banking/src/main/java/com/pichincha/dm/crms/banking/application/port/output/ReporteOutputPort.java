package com.pichincha.dm.crms.banking.application.port.output;

import java.time.LocalDate;

public interface ReporteOutputPort{
	<T> T generarReporte(Long idPersona, LocalDate fechaInicio, LocalDate fechaFin, String formato);
}
