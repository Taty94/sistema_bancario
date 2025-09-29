package com.pichincha.dm.crms.banking.domain.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Persona  {
    private String id;
    private String nombre;
    private String apellido;
    private String genero;
    private String edad;
    private String tipoIdentificacion;
    private String identificacion;
    private String callePrincipal;
    private String calleSecundaria;
    private String numeroCasa;
    private String ciudad;
    private String provincia;
    private String telefono;
}