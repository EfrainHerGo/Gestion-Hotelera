package com.efrain.Common.enums;
import lombok.Getter;

@Getter
public enum EstadoRegistro {

    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");

    private final String codigo;

    EstadoRegistro(String codigo) {
        this.codigo = codigo;
    }
}
