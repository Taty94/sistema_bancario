package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.impl;

import com.pichincha.dm.crms.banking.application.port.input.CuentaInputPort;
import com.pichincha.dm.crms.banking.domain.models.CuentaRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.CuentasApi;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper.CuentaMapper;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CuentaListResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CuentaResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CreateCuentaRequest;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.UpdateCuentaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CuentasApiController implements CuentasApi {
    private final CuentaInputPort cuentaInputPort;
    private final CuentaMapper cuentaMapper;

    @Override
    public ResponseEntity<CuentaResponse> createCuenta(CreateCuentaRequest createCuentaRequest) {
        CuentaRespuesta cuentaRespuesta = cuentaInputPort.crearCuenta(
                cuentaMapper.toCrearCuentaPeticion(createCuentaRequest).getData());
        return new ResponseEntity<>(cuentaMapper.toCuentaResponse(cuentaRespuesta), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CuentaResponse> getCuentaByNumero(String numeroCuenta) {
        CuentaRespuesta cuentaRespuesta = cuentaInputPort.obtenerCuentaPorNumero(numeroCuenta);
        return cuentaRespuesta.getData() != null ?
                ResponseEntity.ok(cuentaMapper.toCuentaResponse(cuentaRespuesta)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(cuentaMapper.toCuentaResponse(cuentaRespuesta));
    }

    @Override
    public ResponseEntity<CuentaResponse> updateCuenta(String numeroCuenta, UpdateCuentaRequest updateCuentaRequest) {
        CuentaRespuesta cuentaActualizada = cuentaInputPort.actualizarCuenta(numeroCuenta,
                cuentaMapper.toCuentaActualizar(updateCuentaRequest.getData()));
        return new ResponseEntity<>(cuentaMapper.toCuentaResponse(cuentaActualizada), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCuenta(String numeroCuenta) {
       cuentaInputPort.eliminarCuenta(numeroCuenta);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<CuentaListResponse> getAllCuentas(Integer page, Integer size, Long clienteId) {
        return ResponseEntity.ok(
                cuentaMapper.toCuentaListResponse(
                        cuentaInputPort.listarCuentas(page, size, clienteId))
        );
    }

}
