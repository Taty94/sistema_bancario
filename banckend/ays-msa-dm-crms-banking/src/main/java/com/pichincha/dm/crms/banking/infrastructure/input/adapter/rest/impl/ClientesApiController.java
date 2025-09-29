package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.impl;

import com.pichincha.dm.crms.banking.application.port.input.ClienteInputPort;
import com.pichincha.dm.crms.banking.domain.models.ClienteRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.ClientesApi;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper.ClienteMapper;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.ClienteListResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.ClienteResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CreateClienteRequest;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.UpdateClienteRequest;
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
public class ClientesApiController implements ClientesApi {
    private final ClienteInputPort  clienteInputPort;
    private final ClienteMapper clienteMapper;
    @Override
    public ResponseEntity<ClienteResponse> createCliente(CreateClienteRequest createClienteRequest) {
       ClienteRespuesta clienteRespuesta = clienteInputPort.crearCliente(
               clienteMapper.toCrearClientePeticion(createClienteRequest).getData());
        return new ResponseEntity<>(clienteMapper.toClienteResponse(clienteRespuesta), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<ClienteResponse> getClienteById(Long id) {
        return clienteInputPort.obtenerClienteporId(id)
                .getData() != null ?
                ResponseEntity.ok(clienteMapper.toClienteResponse(clienteInputPort.obtenerClienteporId(id))) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(clienteMapper.toClienteResponse(clienteInputPort.obtenerClienteporId(id)));
    }

    @Override
    public ResponseEntity<ClienteResponse> updateCliente(Long id, UpdateClienteRequest updateClienteRequest) {
        ClienteRespuesta clienteActualizado= clienteInputPort.actualizarCliente(id,
                clienteMapper.toActualizarClientePeticion(updateClienteRequest).getData());
        return new ResponseEntity<>(clienteMapper.toClienteResponse(clienteActualizado), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Void> deleteCliente(Long id) {
        clienteInputPort.eliminarCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
    }

    @Override
    public ResponseEntity<ClienteListResponse> getAllClientes(Integer page, Integer size, Boolean estado) {
        return  ResponseEntity.ok(
                clienteMapper.toClienteListResponse(
                        clienteInputPort.listarClientes(page, size, estado))
        );
    }


}
