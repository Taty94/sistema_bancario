package com.pichincha.dm.crms.banking.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BddSistemaBancarioException extends RuntimeException {
    private final String mensaje;
    private final HttpStatus codigo;

    public BddSistemaBancarioException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
        this.codigo = null;
    }


}
