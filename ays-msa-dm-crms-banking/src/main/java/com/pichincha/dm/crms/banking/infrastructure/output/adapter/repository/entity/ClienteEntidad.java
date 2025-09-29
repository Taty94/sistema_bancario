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
@Table(name = "SIS_CLIENTE", schema = "public")
public class ClienteEntidad extends PersonaEntidad {

	@Column(name = "CLIENTE_ID", nullable = false, length = 50, unique = true)
	private String clienteId;

	@Column(name = "CONTRASENA", nullable = false, length = 255)
	private String contrasena;

	@Column(name = "ESTADO", nullable = false)
	private Boolean estado;

	@Column(name = "FECHA_CREACION", insertable = false, updatable = false)
	private LocalDateTime fechaCreacion;

	@Column(name = "FECHA_ACTUALIZACION", insertable = false, updatable = false)
	private LocalDateTime fechaActualizacion;
}
