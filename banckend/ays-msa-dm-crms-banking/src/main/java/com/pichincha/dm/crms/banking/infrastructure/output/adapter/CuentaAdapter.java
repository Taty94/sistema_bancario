package com.pichincha.dm.crms.banking.infrastructure.output.adapter;

import com.pichincha.dm.crms.banking.application.port.output.CuentaOutputPort;
import com.pichincha.dm.crms.banking.domain.models.*;
import com.pichincha.dm.crms.banking.domain.util.Constants;
import com.pichincha.dm.crms.banking.infrastructure.exception.BddSistemaBancarioException;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.ClienteRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.CuentaRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.CuentaEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper.CuentaEntidadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.List;

import static com.pichincha.dm.crms.banking.domain.util.Utils.generarNumeroCuentaSeguro;

@Component
@RequiredArgsConstructor
public class CuentaAdapter implements CuentaOutputPort {
    private final CuentaRepositorio cuentaRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final CuentaEntidadMapper cuentaEntidadMapper;

    @Override
    public CuentaRespuesta crearCuenta(Cuenta cuentaData) {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(Long.valueOf(cuentaData.getIdCliente()))
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CLIENTE_NO_ENCONTRADO+cuentaData.getIdCliente(), HttpStatus.BAD_REQUEST));
        CuentaEntidad cuentaEntidad = cuentaEntidadMapper.toCuentaEntidad(cuentaData);
        cuentaEntidad.setCliente(clienteEntidad);
        cuentaEntidad.setSaldoActual(cuentaEntidad.getSaldoInicial());
        cuentaEntidad.setNumeroCuenta(generarNumeroCuentaSeguro());
        Cuenta cuentaCreada = cuentaEntidadMapper.toCuenta(cuentaRepositorio.save(cuentaEntidad));
        cuentaCreada.setNombreCliente(clienteEntidad.getNombre() + " " + clienteEntidad.getApellido());
        return CuentaRespuesta.builder()
                .data(cuentaCreada)
                .message(Constants.CUENTA_CREADA_EXITOSO)
                .build();
    }

    @Override
    public CuentaRespuesta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaRepositorio.findByNumeroCuenta(numeroCuenta)
                .map(cuentaEntidad -> {
                    Cuenta cuenta = cuentaEntidadMapper.toCuenta(cuentaEntidad);
                    ClienteEntidad cliente = cuentaEntidad.getCliente();
                    if (cliente != null) {
                        cuenta.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
                    }
                    return CuentaRespuesta.builder()
                            .data(cuenta)
                            .message(Constants.CUENTA_ENCONTRADA_EXITOSO)
                            .build();
                })
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CUENTA_NO_ENCONTRADA_NUMERO + numeroCuenta, HttpStatus.BAD_REQUEST));
    }

    @Override
    public CuentaRespuesta actualizarCuenta(String numeroCuenta, CuentaActualizar cuentaActualizar) {
        CuentaEntidad cuentaEntidad = cuentaRepositorio.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CUENTA_NO_ENCONTRADA_NUMERO + numeroCuenta, HttpStatus.BAD_REQUEST));

        if (cuentaActualizar.getTipoCuenta() != null) {
            cuentaEntidad.setTipoCuenta(cuentaActualizar.getTipoCuenta());
        }
        CuentaEntidad actualizado = cuentaRepositorio.save(cuentaEntidad);
        Cuenta cuenta = cuentaEntidadMapper.toCuenta(actualizado);
        ClienteEntidad cliente = actualizado.getCliente();
        if (cliente != null) {
            cuenta.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        }
        return CuentaRespuesta.builder()
                .data(cuenta)
                .message(Constants.CUENTA_ACTUALIZADA_EXITOSO)
                .build();
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        CuentaEntidad cuentaEntidad = cuentaRepositorio.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CUENTA_NO_ENCONTRADA_NUMERO + numeroCuenta, HttpStatus.BAD_REQUEST));
        if (!cuentaEntidad.getEstado()) {
            throw new BddSistemaBancarioException(Constants.CUENTA_YA_ELIMINADA, HttpStatus.BAD_REQUEST);
        }
        cuentaEntidad.setEstado(false);
        cuentaRepositorio.save(cuentaEntidad);
    }

    @Override
    public CuentaListRespuesta listarCuentas(Integer page, Integer size, Long clienteId) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 20);
        Page<CuentaEntidad> pageResult;
        if (clienteId != null) {
            pageResult = cuentaRepositorio.findAllByCliente_IdPersona(clienteId, pageable);
        } else {
            pageResult = cuentaRepositorio.findAll(pageable);
        }
        List<Cuenta> cuentas = pageResult.getContent().stream()
                .map(cuentaEntidad -> {
                    Cuenta cuenta = cuentaEntidadMapper.toCuenta(cuentaEntidad);
                    ClienteEntidad cliente = cuentaEntidad.getCliente();
                    if (cliente != null) {
                        cuenta.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
                    }
                    return cuenta;
                })
                .toList();
        PaginationInfo pagination = PaginationInfo.builder()
                .page(String.valueOf(pageResult.getNumber()))
                .size(String.valueOf(pageResult.getSize()))
                .totalElements(String.valueOf(pageResult.getTotalElements()))
                .totalPages(String.valueOf(pageResult.getTotalPages()))
                .first(String.valueOf(pageResult.isFirst()))
                .last(String.valueOf(pageResult.isLast()))
                .build();
        return CuentaListRespuesta.builder()
                .data(cuentas)
                .pagination(pagination)
                .build();
    }
}
