package com.efrain.Common.enums;

import com.efrain.Common.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum EstadoHabitacion {

    DISPONIBLE(1L, "DISPONIBLE"),
    OCUPADA(2L, "OCUPADA"),
    MANTENIMIENTO(3L, "MANTENIMIENTO"),
    LIMPIEZA(4L, "LIMPIEZA");

    private final Long codigo;
    private final String descripcion;

    public static EstadoHabitacion obtenerEstadoHabitacionPorCodigo(Long codigo) {
        for (EstadoHabitacion estado : values()) {
            if (Objects.equals(estado.codigo, codigo)) {
                return estado;
            }
        }

        throw new RecursoNoEncontradoException(
                "Codigo de estado de habitación no válido: " + codigo
        );
    }
}