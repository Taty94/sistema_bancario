package com.pichincha.dm.crms.banking.application.port.input;

import com.pichincha.dm.crms.banking.domain.models.Cliente;
import com.pichincha.dm.crms.banking.domain.models.ClienteActualizar;
import com.pichincha.dm.crms.banking.domain.models.ClienteListRespuesta;
import com.pichincha.dm.crms.banking.domain.models.ClienteRespuesta;

public interface ClienteInputPort {
    ClienteRespuesta crearCliente(Cliente clienteData);
    ClienteRespuesta obtenerClienteporId(Long id);
    ClienteRespuesta actualizarCliente(Long id, ClienteActualizar clienteActualizar);
    ClienteListRespuesta listarClientes(Integer page, Integer size, Boolean estado);
    void eliminarCliente(Long id);
}
