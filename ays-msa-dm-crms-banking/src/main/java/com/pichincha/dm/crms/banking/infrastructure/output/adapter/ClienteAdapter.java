package com.pichincha.dm.crms.banking.infrastructure.output.adapter;

import com.pichincha.dm.crms.banking.application.port.output.ClienteOutputPort;
import com.pichincha.dm.crms.banking.domain.models.*;
import com.pichincha.dm.crms.banking.domain.util.Constants;
import com.pichincha.dm.crms.banking.domain.util.Utils;
import com.pichincha.dm.crms.banking.infrastructure.exception.BddSistemaBancarioException;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.ClienteRepositorio;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper.ClienteEntidadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutputPort {
    private final ClienteEntidadMapper clienteEntidadMapper;
    private final ClienteRepositorio clienteRepositorio;

    @Override
    public ClienteRespuesta crearCliente(Cliente clienteData) {
        var clienteEntidad = clienteEntidadMapper.toClienteEntidad(clienteData);
        clienteEntidad.setClienteId(Utils.generateClientId());
        return ClienteRespuesta.builder()
                        .data(clienteEntidadMapper.toCliente(clienteRepositorio.save(clienteEntidad)))
                .message(Constants.CLIENTE_CREADO_EXITOSO).build();
    }

    @Override
    public ClienteRespuesta obtenerClienteporId(Long id) {
        return clienteRepositorio.findById(id)
                .map(clienteEntidadMapper::toCliente)
                .map(cliente -> ClienteRespuesta.builder()
                        .data(cliente)
                        .message(Constants.CLIENTE_ENCONTRADO_EXITOSO)
                        .build())
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CLIENTE_NO_ENCONTRADO + id , HttpStatus.BAD_REQUEST ));
    }

    @Override
    public ClienteRespuesta actualizarCliente(Long id, ClienteActualizar clienteActualizar) {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(id)
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CLIENTE_NO_ENCONTRADO + id , HttpStatus.BAD_REQUEST ));
        clienteEntidad.setNombre(clienteActualizar.getNombre());
        clienteEntidad.setApellido(clienteActualizar.getApellido());
        clienteEntidad.setCallePrincipal(clienteActualizar.getCallePrincipal());
        clienteEntidad.setCalleSecundaria(clienteActualizar.getCalleSecundaria());
        clienteEntidad.setNumeroCasa(clienteActualizar.getNumeroCasa());
        clienteEntidad.setCiudad(clienteActualizar.getCiudad());
        clienteEntidad.setProvincia(clienteActualizar.getProvincia());
        clienteEntidad.setTelefono(clienteActualizar.getTelefono());
        if (clienteActualizar.getContrasena() != null && !clienteActualizar.getContrasena().isBlank()) {
            clienteEntidad.setContrasena(clienteActualizar.getContrasena());
        }
        ClienteEntidad actualizado = clienteRepositorio.save(clienteEntidad);
        return ClienteRespuesta.builder()
                .data(clienteEntidadMapper.toCliente(actualizado))
                .message(Constants.CLIENTE_ACTUALIZADO_EXITOSO)
                .build();
    }

    @Override
    public void eliminarCliente(Long id) {
        ClienteEntidad clienteEntidad = clienteRepositorio.findById(id)
                .orElseThrow(() -> new BddSistemaBancarioException(Constants.CLIENTE_NO_ENCONTRADO + id , HttpStatus.BAD_REQUEST ));
        if (!clienteEntidad.getEstado()) {
            throw new BddSistemaBancarioException(Constants.CLIENTE_YA_ELIMINADO, HttpStatus.BAD_REQUEST);
        }
        clienteEntidad.setEstado(false);
        clienteRepositorio.save(clienteEntidad);
    }

    @Override
    public ClienteListRespuesta listarClientes(Integer page, Integer size, Boolean estado) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 20);
        Page<ClienteEntidad> pageResult;

        if (estado != null) {
            pageResult = clienteRepositorio.findAllByEstado(estado, pageable);
        } else {
            pageResult = clienteRepositorio.findAll(pageable);
        }

        List<Cliente> clientes = pageResult.getContent().stream()
                .map(clienteEntidadMapper::toCliente)
                .toList();

        PaginationInfo pagination= PaginationInfo.builder()
                .page(String.valueOf(pageResult.getNumber()))
                        .size(String.valueOf(pageResult.getSize()))
                .totalElements(String.valueOf(pageResult.getTotalElements()))
                .totalPages(String.valueOf(pageResult.getTotalPages()))
                .first(String.valueOf(pageResult.isFirst()))
                .last(String.valueOf(pageResult.isLast())).build();

        return ClienteListRespuesta.builder()
                .data(clientes)
                .pagination(pagination)
                .build();
    }
}
