package com.pichincha.dm.crms.banking.infrastructure.output.adapter.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SIS_PERSONA", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSONA")
    private Long idPersona;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "GENERO", nullable = false, length = 10)
    private String genero;

    @Column(name = "EDAD", nullable = false)
    private Integer edad;

    @Column(name = "TIPO_IDENTIFICACION", nullable = false, length = 10)
    private String tipoIdentificacion;

    @Column(name = "IDENTIFICACION", nullable = false, length = 20)
    private String identificacion;

    @Column(name = "CALLE_PRINCIPAL", length = 100)
    private String callePrincipal;

    @Column(name = "CALLE_SECUNDARIA", length = 100)
    private String calleSecundaria;

    @Column(name = "NUMERO_CASA", length = 10)
    private String numeroCasa;

    @Column(name = "CIUDAD", length = 50)
    private String ciudad;

    @Column(name = "PROVINCIA", length = 50)
    private String provincia;

    @Column(name = "TELEFONO", length = 15)
    private String telefono;

    @Column(name = "FECHA_CREACION", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;
}
