package com.pichincha.dm.crms.banking.infrastructure.output.adapter;

import com.pichincha.dm.crms.banking.application.port.output.MovimientoOutputPort;
import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;
import com.pichincha.dm.crms.banking.domain.models.PaginationInfo;
import com.pichincha.dm.crms.banking.domain.util.Constants;
import com.pichincha.dm.crms.banking.infrastructure.exception.BddSistemaBancarioException;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper.MovimientoEntidadMapper;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.CuentaRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.MovimientoRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.CuentaEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.MovimientoEntidad;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MovimientoAdapter implements MovimientoOutputPort {

    private final MovimientoRepositorio movimientoRepositorio;
    private final CuentaRepositorio cuentaRepositorio;
    private final MovimientoEntidadMapper movimientoEntidadMapper;

    @Override
    public MovimientoRespuesta crearMovimiento(Movimiento movimientoData) {
        CuentaEntidad cuentaEntidad = obtenerCuentaPorId(movimientoData.getNumeroCuenta());
        validarMovimiento(movimientoData);

        BigDecimal saldoAnterior = cuentaEntidad.getSaldoActual();
        BigDecimal valorMovimiento = new BigDecimal(movimientoData.getValor());
        String tipoMovimiento = movimientoData.getTipoMovimiento();

        BigDecimal saldoPosterior = calcularSaldoPosterior(saldoAnterior, valorMovimiento, tipoMovimiento);

        actualizarSaldoCuenta(cuentaEntidad, saldoPosterior);

        MovimientoEntidad entidad = construirMovimientoEntidad(movimientoData, cuentaEntidad, saldoAnterior, saldoPosterior);

        MovimientoEntidad guardado = movimientoRepositorio.save(entidad);
        Movimiento movimiento = movimientoEntidadMapper.toMovimiento(guardado);

        if (guardado.getCuenta() != null) {
            movimiento.setNumeroCuenta(guardado.getCuenta().getNumeroCuenta());
            if (guardado.getCuenta().getCliente() != null) {
                movimiento.setNombreCliente(
                        guardado.getCuenta().getCliente().getNombre() + " " +
                                guardado.getCuenta().getCliente().getApellido()
                );
            }
        }
        return MovimientoRespuesta.builder()
                .data(movimiento)
                .message(Constants.MOVIMIENTO_CREADO_EXITOSAMENTE)
                .build();
    }

    @Override
    public MovimientoListRespuesta listarMovimientos(int page, int size, String numeroCuenta) {
        Page<MovimientoEntidad> pageResult = obtenerMovimientosPaginados(page, size, numeroCuenta);
        List<Movimiento> movimientos = pageResult.getContent().stream()
                .map(entidad -> {
                    Movimiento movimiento = movimientoEntidadMapper.toMovimiento(entidad);
                    if (entidad.getCuenta() != null) {
                        movimiento.setNumeroCuenta(entidad.getCuenta().getNumeroCuenta());
                        if (entidad.getCuenta().getCliente() != null) {
                            movimiento.setNombreCliente(
                                    entidad.getCuenta().getCliente().getNombre() + " " +
                                            entidad.getCuenta().getCliente().getApellido()
                            );
                        }
                    }
                    return movimiento;
                })
                .toList();
        PaginationInfo pagination = construirPaginationInfo(pageResult);
        return MovimientoListRespuesta.builder()
                .data(movimientos)
                .pagination(pagination)
                .build();
    }

    private CuentaEntidad obtenerCuentaPorId(String numeroCuenta) {
        return cuentaRepositorio.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CUENTA_NO_ENCONTRADA_NUMERO + numeroCuenta,
                        HttpStatus.NOT_FOUND));
    }

    private void validarMovimiento(Movimiento movimiento) {
        if (movimiento.getValor() == null || movimiento.getValor().compareTo(String.valueOf(BigDecimal.ZERO)) <= 0) {
            throw new IllegalArgumentException(Constants.VALOR_MOVIMIENTO_INVALIDO);
        }
        if (!"Crédito".equalsIgnoreCase(movimiento.getTipoMovimiento()) &&
                !"Débito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            throw new IllegalArgumentException(Constants.TiPO_MOVIMIENTO_NO_SOPORTADO + movimiento.getTipoMovimiento());
        }
    }

    private BigDecimal calcularSaldoPosterior(BigDecimal saldoAnterior, BigDecimal valor, String tipoMovimiento) {
        if ("Crédito".equalsIgnoreCase(tipoMovimiento)) {
            return saldoAnterior.add(valor);
        } else if ("Débito".equalsIgnoreCase(tipoMovimiento)) {
            BigDecimal saldoPosterior = saldoAnterior.subtract(valor.abs());
            if (saldoPosterior.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(Constants.SALDO_INSUFICIENTE);
            }
            return saldoPosterior;
        }
        throw new IllegalArgumentException(Constants.TiPO_MOVIMIENTO_NO_SOPORTADO + tipoMovimiento);
    }

    private void actualizarSaldoCuenta(CuentaEntidad cuentaEntidad, BigDecimal saldoPosterior) {
        cuentaEntidad.setSaldoActual(saldoPosterior);
        cuentaRepositorio.save(cuentaEntidad);
    }

    private MovimientoEntidad construirMovimientoEntidad(Movimiento movimientoData, CuentaEntidad cuentaEntidad,
                                                         BigDecimal saldoAnterior, BigDecimal saldoPosterior) {
        MovimientoEntidad entidad = movimientoEntidadMapper.toMovimientoEntidad(movimientoData);
        entidad.setCuenta(cuentaEntidad);
        entidad.setFecha(OffsetDateTime.now(ZoneOffset.UTC));
        entidad.setSaldoAnterior(saldoAnterior);
        entidad.setSaldoPosterior(saldoPosterior);
        return entidad;
    }

    private Page<MovimientoEntidad> obtenerMovimientosPaginados(int page, int size, String numeroCuenta) {
        Pageable pageable = PageRequest.of(page, size);
        if (numeroCuenta != null || !numeroCuenta.isBlank()) {
            return movimientoRepositorio.findAllByCuenta_numeroCuenta(numeroCuenta, pageable);
        } else {
            return movimientoRepositorio.findAll(pageable);
        }
    }

    private PaginationInfo construirPaginationInfo(Page<?> pageResult) {
        return PaginationInfo.builder()
                .page(String.valueOf(pageResult.getNumber()))
                .size(String.valueOf(pageResult.getSize()))
                .totalElements(String.valueOf(pageResult.getTotalElements()))
                .totalPages(String.valueOf(pageResult.getTotalPages()))
                .first(String.valueOf(pageResult.isFirst()))
                .last(String.valueOf(pageResult.isLast()))
                .build();
    }
}