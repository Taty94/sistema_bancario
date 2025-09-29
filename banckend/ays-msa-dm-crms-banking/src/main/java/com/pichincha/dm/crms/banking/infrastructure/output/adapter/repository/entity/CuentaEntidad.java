package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SIS_CUENTA", schema = "public")
public class CuentaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA")
    private Long idCuenta;

    @Column(name = "NUMERO_CUENTA", nullable = false, length = 20, unique = true)
    private String numeroCuenta;

    @Column(name = "TIPO_CUENTA", nullable = false, length = 10)
    private String tipoCuenta;

    @Column(name = "SALDO_INICIAL", nullable = false, precision = 15, scale = 2)
    private java.math.BigDecimal saldoInicial;

    @Column(name = "SALDO_ACTUAL", nullable = false, precision = 15, scale = 2)
    private java.math.BigDecimal saldoActual;

    @Column(name = "ESTADO", nullable = false)
    private Boolean estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", nullable = false)
    private ClienteEntidad cliente;

    @Column(name = "FECHA_CREACION",insertable = false, updatable = false)
    private java.sql.Timestamp fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION",insertable = false, updatable = false)
    private java.sql.Timestamp fechaActualizacion;
}
