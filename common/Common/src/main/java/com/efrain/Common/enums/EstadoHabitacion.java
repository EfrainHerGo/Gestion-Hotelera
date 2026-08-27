package com.efrain.Common.enums;

import lombok.Getter;

@Getter
public enum EstadoHabitacion {

    DISPONIBLE(1, "DISPONIBLE"),
    OCUPADA(2, "OCUPADA"),
    LIMPIE(3, "LIMPIE"),
    MANTENIMIENTO(4, "MANTENIMIENTO");

    private final Integer id;
    private final String codigo;

    EstadoHabitacion(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }
}