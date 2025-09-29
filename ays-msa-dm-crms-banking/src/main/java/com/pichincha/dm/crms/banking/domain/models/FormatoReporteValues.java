package com.pichincha.dm.crms.banking.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;

/**
 * <sup><i>(ES)</i></sup> Formato del reporte a generar<br><br>
 * <sup><i>(EN)</i></sup> Report format to generate
 */
@Getter
public enum FormatoReporteValues implements Serializable {
    
    JSON("json"),
    PDF("pdf");

    @JsonValue
    private final String value;

    FormatoReporteValues(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static FormatoReporteValues fromValue(String value) {
        for (FormatoReporteValues formato : FormatoReporteValues.values()) {
            if (formato.value.equals(value)) {
                return formato;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}