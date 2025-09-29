package com.pichincha.dm.crms.banking.application.port.output;

import com.pichincha.dm.crms.banking.domain.models.*;

public interface ClienteOutputPort {
    ClienteRespuesta crearCliente(Cliente clienteData);
    ClienteListRespuesta listarClientes(Integer page, Integer size, Boolean estado);
    ClienteRespuesta obtenerClienteporId(Long id);
    ClienteRespuesta actualizarCliente(Long id, ClienteActualizar clienteActualizar);
    void eliminarCliente(Long id);
}
