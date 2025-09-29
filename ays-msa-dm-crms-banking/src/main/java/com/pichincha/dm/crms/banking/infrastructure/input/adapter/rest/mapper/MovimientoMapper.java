package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper;

import com.pichincha.dm.crms.banking.domain.models.Movimiento;
import com.pichincha.dm.crms.banking.domain.models.MovimientoRespuesta;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.MovimientoListResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.MovimientoResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.TipoMovimientoValues;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {
    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

    @Mapping(target = "tipoMovimiento", source = "tipoMovimiento.value")
    Movimiento toMovimiento(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Movimiento movimiento);

    //Response
    MovimientoResponse toMovimientoResponse(MovimientoRespuesta movimientoRespuesta);
    @Mapping(target = "tipoMovimiento", source = "tipoMovimiento", qualifiedByName = "totipoMovimientoEnum")
    com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Movimiento toMovimiento(Movimiento movimiento);
    @Named("totipoMovimientoEnum")
    default TipoMovimientoValues totipoMovimientoEnum(String value) {
        return TipoMovimientoValues.fromValue(value);
    }

    MovimientoListResponse toMovimientoListResponse(com.pichincha.dm.crms.banking.domain.models.MovimientoListRespuesta movimientoListRespuesta);
}
