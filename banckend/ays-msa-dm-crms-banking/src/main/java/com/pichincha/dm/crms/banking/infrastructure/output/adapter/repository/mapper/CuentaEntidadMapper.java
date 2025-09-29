package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper;

import com.pichincha.dm.crms.banking.domain.models.Cuenta;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.CuentaEntidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CuentaEntidadMapper {
    @Mapping(target = "idCliente", source = "cliente.idPersona")
    @Mapping(target = "id", source = "idCuenta")
    @Mapping(target = "nombreCliente", source = "cliente", qualifiedByName = "mapNombreCompleto")
    Cuenta toCuenta(CuentaEntidad cuentaEntidad);

    @Named("mapNombreCompleto")
    default String mapNombreCompleto(ClienteEntidad cliente) {
        return cliente.getNombre() + " " + cliente.getApellido();
    }
    CuentaEntidad toCuentaEntidad(Cuenta cuenta);
}
