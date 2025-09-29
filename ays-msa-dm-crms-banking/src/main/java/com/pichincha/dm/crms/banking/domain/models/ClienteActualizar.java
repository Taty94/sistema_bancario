package com.pichincha.dm.crms.banking.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteActualizar {
    private String nombre;
    private String apellido;
    private String genero;
    private String edad;
    private String callePrincipal;
    private String calleSecundaria;
    private String numeroCasa;
    private String ciudad;
    private String provincia;
    private String telefono;
    private String contrasena;
    private String estado;
}