package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SIS_MOVIMIENTO", schema = "public")
public class MovimientoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private Long idMovimiento;

    @ManyToOne
    @JoinColumn(name = "ID_CUENTA", referencedColumnName = "ID_CUENTA", nullable = false)
    private CuentaEntidad cuenta;

    @Column(name = "FECHA", nullable = false)
    private OffsetDateTime fecha;

    @Column(name = "TIPO_MOVIMIENTO", nullable = false, length = 10)
    private String tipoMovimiento;

    @Column(name = "VALOR", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "SALDO_ANTERIOR", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoAnterior;

    @Column(name = "SALDO_POSTERIOR", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoPosterior;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "REFERENCIA", length = 50)
    private String referencia;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private Boolean estado;

    @Column(name = "FECHA_CREACION",insertable = false, updatable = false)
    private OffsetDateTime fechaCreacion;
}
