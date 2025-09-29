package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.impl;


import com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.MovimientosApi;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CreateMovimientoRequest;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.MovimientoListResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.MovimientoResponse;
import com.pichincha.dm.crms.banking.application.port.input.MovimientoInputPort;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper.MovimientoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class MovimientosApiController implements MovimientosApi {
    private final MovimientoInputPort movimientoInputPort;
    private final MovimientoMapper movimientoMapper;

    @Override
    public ResponseEntity<MovimientoResponse> createMovimiento(CreateMovimientoRequest createMovimientoRequest) {
        MovimientoRespuesta respuesta = movimientoInputPort.crearMovimiento(
                movimientoMapper.toMovimiento(createMovimientoRequest.getData()));
        return new ResponseEntity<>(movimientoMapper.toMovimientoResponse(respuesta), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MovimientoListResponse> getAllMovimientos(Integer page, Integer size, String nombreCuenta) {
        MovimientoListRespuesta respuesta = movimientoInputPort.listarMovimientos(page,size, nombreCuenta);
        return ResponseEntity.ok(movimientoMapper.toMovimientoListResponse(respuesta));
    }
}
