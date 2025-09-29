package com.pichincha.dm.crms.banking.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@SuperBuilder
public class Movimiento {
    private String id;
    private String idCuenta;
    private String numeroCuenta;
    private String nombreCliente;
    private OffsetDateTime fecha;
    private String tipoMovimiento;
    private String valor;
    private String saldoAnterior;
    private String saldoPosterior;
    private String descripcion;
    private String referencia;
    private String estado;
}