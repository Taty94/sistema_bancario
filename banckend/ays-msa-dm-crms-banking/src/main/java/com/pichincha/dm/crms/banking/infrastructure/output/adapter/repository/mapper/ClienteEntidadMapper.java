package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper;

import com.pichincha.dm.crms.banking.domain.models.Cliente;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClienteEntidadMapper {

    @Mapping(target = "idPersona", source = "id")
    ClienteEntidad toClienteEntidad(Cliente cliente);
    @Mapping(target = "id", source = "idPersona")
    Cliente toCliente(ClienteEntidad clienteEntidad);

}
