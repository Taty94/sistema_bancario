package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.mapper;

import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.ClienteEntidad;
import com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity.MovimientoEntidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoEntidadMapper {
    MovimientoEntidadMapper INSTANCE = Mappers.getMapper(MovimientoEntidadMapper.class);

    @Mappings({
        @Mapping(source = "cuenta.idCuenta", target = "idCuenta"),
        @Mapping(source = "idMovimiento", target = "id"),
        @Mapping(source = "cuenta.numeroCuenta", target = "numeroCuenta" ),
            @Mapping(source = "cuenta.cliente", target = "nombreCliente" , qualifiedByName = "mapNombreCompleto")
    })
    Movimiento toMovimiento(MovimientoEntidad entidad);
    @Named("mapNombreCompleto")
    default String mapNombreCompleto(ClienteEntidad cliente) {
        return cliente.getNombre() + " " + cliente.getApellido();
    }
    @Mappings({
        @Mapping(target = "cuenta", ignore = true),
        @Mapping(source = "id", target = "idMovimiento")
    })
    MovimientoEntidad toMovimientoEntidad(Movimiento movimiento);
}
