package com.pichincha.dm.crms.banking.application.service;

import com.pichincha.dm.crms.banking.application.port.input.ClienteInputPort;
import com.pichincha.dm.crms.banking.application.port.output.ClienteOutputPort;
import com.pichincha.dm.crms.banking.domain.models.Cliente;
import com.pichincha.dm.crms.banking.domain.models.ClienteActualizar;
import com.pichincha.dm.crms.banking.domain.models.ClienteListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.ClienteRespuesta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteInputPort {
    private final ClienteOutputPort clienteOutputPort;
    @Override
    public ClienteRespuesta crearCliente(Cliente clienteData) {
        return clienteOutputPort.crearCliente(clienteData);
    }

    @Override
    public ClienteRespuesta obtenerClienteporId(Long id) {
        return clienteOutputPort.obtenerClienteporId(id);
    }

    @Override
    public ClienteRespuesta actualizarCliente(Long id, ClienteActualizar clienteActualizar) {
        return clienteOutputPort.actualizarCliente(id, clienteActualizar);
    }
    @Override
    public ClienteListRespuesta listarClientes(Integer page, Integer size, Boolean estado) {
        return clienteOutputPort.listarClientes(page, size, estado);
    }
    @Override
    public void eliminarCliente(Long id) {
        clienteOutputPort.eliminarCliente(id);
    }
}
