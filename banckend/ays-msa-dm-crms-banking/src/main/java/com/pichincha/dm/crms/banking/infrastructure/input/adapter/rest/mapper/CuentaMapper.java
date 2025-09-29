package com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.mapper;

import com.pichincha.dm.crms.banking.domain.models.*;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CreateCuentaRequest;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CuentaListResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CuentaResponse;
import com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.TipoCuentaValues;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CrearCuentaPeticion toCrearCuentaPeticion(CreateCuentaRequest createCuentaRequest);
    @Mapping(target = "tipoCuenta", source = "tipoCuenta.value")
    Cuenta toCuenta(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Cuenta cuentaData);

    //Response
    CuentaResponse toCuentaResponse(CuentaRespuesta cuentaRespuesta);
    @Mapping(target = "tipoCuenta", source = "tipoCuenta",  qualifiedByName = "totipoCuentaEnum")
    com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.Cuenta toCuentaFromResponse(Cuenta cuenta);
    @Named("totipoCuentaEnum")
    default TipoCuentaValues totipoCuentaEnum(String value) {
        return TipoCuentaValues.fromValue(value);
    }

    @Mapping(target = "tipoCuenta", source = "tipoCuenta.value")
    CuentaActualizar toCuentaActualizar(com.pichincha.dm.crms.banking.infrastructure.input.adapter.rest.models.CuentaUpdate cuentaUpdate);
    CuentaListResponse toCuentaListResponse(CuentaListRespuesta cuentaListRespuesta);

}
