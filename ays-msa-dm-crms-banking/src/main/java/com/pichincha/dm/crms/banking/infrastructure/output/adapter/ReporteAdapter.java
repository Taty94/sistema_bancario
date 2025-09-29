package com.pichincha.dm.crms.banking.infrastructure.output.adapter;
import com.pichincha.dm.crms.banking.infrastructure.exception.BddSistemaBancarioException;
import lombok.RequiredArgsConstructor;

import com.pichincha.dm.crms.banking.application.port.output.ReporteOutputPort;
import com.pichincha.dm.crms.banking.domain.models.*;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.MovimientoRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.CuentaEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.MovimientoEntidad;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReporteAdapter implements ReporteOutputPort {
    private final MovimientoRepositorio movimientoRepositorio;
    private final ReportePdfService reportePdfService;

    @Override
    public <T> T generarReporte(Long idPersona, LocalDate fechaInicio, LocalDate fechaFin, String formato) {
        ReporteEstadoCuenta reporte = armarReporteEstadoCuenta(idPersona, fechaInicio, fechaFin);
        if ("pdf".equalsIgnoreCase(formato)) {
            return (T) reportePdfService.generarRespuestaPdf(reporte);
        } else {
            return (T) armarReporteJsonRespuesta(reporte);
        }
    }

    private ReporteEstadoCuenta armarReporteEstadoCuenta(Long idPersona, LocalDate fechaInicio, LocalDate fechaFin) {
        java.time.OffsetDateTime fechaInicioODT = fechaInicio.atStartOfDay().atOffset(java.time.ZoneOffset.UTC);
        java.time.OffsetDateTime fechaFinODT = fechaFin.atTime(23,59,59).atOffset(java.time.ZoneOffset.UTC);

        List<MovimientoEntidad> movimientos = movimientoRepositorio.findByCuenta_Cliente_IdPersonaAndFechaBetween(idPersona, fechaInicioODT, fechaFinODT);
        if (movimientos == null || movimientos.isEmpty()) {
            throw new BddSistemaBancarioException("No se encontraron movimientos para el cliente en el rango de fechas", HttpStatus.NOT_FOUND);
        }

        java.util.Map<CuentaEntidad, List<MovimientoEntidad>> movimientosPorCuenta = movimientos.stream()
            .collect(Collectors.groupingBy(MovimientoEntidad::getCuenta));

        ClienteEntidad clienteEntidad = movimientos.get(0).getCuenta().getCliente();

        ReporteEstadoCuentaCliente cliente = ReporteEstadoCuentaCliente.builder()
            .clienteId(clienteEntidad.getClienteId())
            .nombre(clienteEntidad.getNombre() + " " + clienteEntidad.getApellido())
            .build();

        List<ReporteEstadoCuentaCuentas> cuentasReporte = movimientosPorCuenta.entrySet().stream()
            .map(entry -> {
                CuentaEntidad cuenta = entry.getKey();
                List<MovimientoEntidad> movs = entry.getValue();

                java.math.BigDecimal saldoInicial = cuenta.getSaldoInicial();
                java.math.BigDecimal saldoCuentaFinal = cuenta.getSaldoActual();
                int totalMovimientos = movs.size();

                ReporteEstadoCuentaResumenCuenta resumenCuenta = ReporteEstadoCuentaResumenCuenta.builder()
                    .saldoInicial(saldoInicial)
                    .totalMovimientos(totalMovimientos)
                    .saldoFinal(saldoCuentaFinal.toString())
                    .build();

                List<Movimiento> movimientosReporte = movs.stream().map(mov ->
                    Movimiento.builder()
                        .id(String.valueOf(mov.getIdMovimiento()))
                        .tipoMovimiento(mov.getTipoMovimiento())
                        .valor(mov.getValor().toString())
                        .descripcion(mov.getDescripcion())
                        .fecha(mov.getFecha())
                        .estado(String.valueOf(mov.getEstado()))
                        .referencia(mov.getReferencia())
                        .saldoAnterior(mov.getSaldoAnterior() != null ? mov.getSaldoAnterior().toString() : null)
                        .saldoPosterior(mov.getSaldoPosterior() != null ? mov.getSaldoPosterior().toString() : null)
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .nombreCliente(clienteEntidad.getNombre() + " " + clienteEntidad.getApellido())
                        .build()
                ).collect(Collectors.toList());

                return ReporteEstadoCuentaCuentas.builder()
                    .numeroCuenta(cuenta.getNumeroCuenta())
                    .tipoCuenta(cuenta.getTipoCuenta())
                    .resumenCuenta(resumenCuenta)
                    .movimientos(movimientosReporte)
                    .build();
            })
            .collect(Collectors.toList());

        java.math.BigDecimal totalCreditos = movimientos.stream()
            .filter(mov -> "Crédito".equalsIgnoreCase(mov.getTipoMovimiento()))
            .map(MovimientoEntidad::getValor)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        java.math.BigDecimal totalDebitos = movimientos.stream()
            .filter(mov -> "Débito".equalsIgnoreCase(mov.getTipoMovimiento()))
            .map(MovimientoEntidad::getValor)
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        java.math.BigDecimal saldoFinal = cuentasReporte.stream()
            .map(c -> new java.math.BigDecimal(c.getResumenCuenta().getSaldoFinal()))
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        ReporteEstadoCuentaResumenGeneral resumenGeneral = ReporteEstadoCuentaResumenGeneral.builder()
            .totalCreditos(totalCreditos)
            .totalDebitos(totalDebitos)
            .saldoFinal(saldoFinal)
            .build();

        return ReporteEstadoCuenta.builder()
            .fechaInicio(fechaInicio)
            .fechaFin(fechaFin)
            .cliente(cliente)
            .resumenGeneral(resumenGeneral)
            .cuentas(cuentasReporte)
            .build();
    }

    private ReporteRespuesta armarReporteJsonRespuesta(ReporteEstadoCuenta reporte) {
        return ReporteRespuesta.builder()
            .data(reporte)
            .message("Reporte generado exitosamente")
            .build();
    }
}
