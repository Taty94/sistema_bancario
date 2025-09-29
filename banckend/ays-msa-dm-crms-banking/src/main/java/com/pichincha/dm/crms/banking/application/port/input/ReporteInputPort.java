package com.pichincha.dm.crms.banking.application.port.input;

import java.time.LocalDate;

public interface ReporteInputPort{
	<T> T generarReporte(Long idPersona, LocalDate fechaInicio, LocalDate fechaFin, String formato);
}
