package com.pichincha.dm.crms.banking.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Cliente extends Persona {
    private String clienteId;
    private String contrasena;
    private String estado;
}