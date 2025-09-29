package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper;

import com.pichincha.dm.crms.banking.domain.models.*;
import com.pichincha.dm.crms.banking.domain.models.Cliente;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.*;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.TipoIdentificacionValues;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ClienteMapper {

    CrearClientePeticion toCrearClientePeticion(CreateClienteRequest peticion);
    @Mapping(target = "genero", source = "genero.value")
    @Mapping(target = "tipoIdentificacion", source = "tipoIdentificacion.value")
    Cliente toCliente(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Cliente cliente);

    ActualizarClientePeticion toActualizarClientePeticion(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.UpdateClienteRequest peticion);
    @Mapping(target = "genero", source = "genero.value")
    ClienteActualizar toClienteActualizar(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.ClienteUpdate clienteUpdate);


    //Response
    ClienteResponse toClienteResponse(ClienteRespuesta respuesta);
    @Mapping(target = "genero", source = "genero",  qualifiedByName = "toGeneroEnum")
    @Mapping(target = "tipoIdentificacion", source = "tipoIdentificacion", qualifiedByName = "toTipoIdentificacionEnum")
    com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Cliente toClienteFromResponse(Cliente cliente);

    ClienteListResponse toClienteListResponse(ClienteListRespuesta respuesta);

    @Named("toGeneroEnum")
    default GeneroValues toGeneroEnum(String value) {
        return GeneroValues.fromValue(value);
    }
    @Named("toTipoIdentificacionEnum")
    default TipoIdentificacionValues toTipoIdentificacionEnum(String value) {
        return TipoIdentificacionValues.fromValue(value);
    }
}
