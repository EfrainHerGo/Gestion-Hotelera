package com.efrain.Common.enums;

import lombok.Getter;

@Getter
public enum EstadoHabitacion {

    DISPONIBLE("DISPONIBLE"),
    OCUPADA("OCUPADA"),
    MANTENIMIENTO("MANTENIMIENTO");

    private final String codigo;

    EstadoHabitacion(String codigo) {
        this.codigo = codigo;
    }
}