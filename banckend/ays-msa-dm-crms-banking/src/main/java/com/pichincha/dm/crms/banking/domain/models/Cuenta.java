package com.pichincha.dm.crms.banking.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cuenta{
    private String id;
    @JsonIgnore
    private String idCliente;
    private String nombreCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private String saldoInicial;
    private String saldoActual;
    private String estado;
}