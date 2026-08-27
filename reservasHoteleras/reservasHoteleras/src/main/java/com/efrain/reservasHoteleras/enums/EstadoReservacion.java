package com.efrain.reservasHoteleras.enums;

import com.efrain.Common.exceptions.RecursoNoEncontradoException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum EstadoReservacion {

    CONFIRMADA(1L, "Reserva confirmada", true, true) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return EnumSet.of(EN_CURSO, CANCELADA);
        }
    },
    EN_CURSO(2L, "Huésped hospedado (Check-in)", true, false) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return EnumSet.of(FINALIZADA);
        }
    },
    FINALIZADA(3L, "Reserva completada (Check-out)", false, false) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return EnumSet.noneOf(EstadoReservacion.class);
        }
    },
    CANCELADA(4L, "Reserva cancelada", false, false) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return EnumSet.noneOf(EstadoReservacion.class);
        }
    };

    private final Long codigo;
    private final String descripcion;
    private final boolean actualizable;
    private final boolean eliminable;

    public abstract Set<EstadoReservacion> puedeCambiar();

    public boolean puedeCambiarA(EstadoReservacion nuevoEstado) {
        return puedeCambiar().contains(nuevoEstado);
    }

    public static EstadoReservacion obtenerEstadoReservacionPorCodigo(Long codigo) {
        for (EstadoReservacion e : values()) {
            if (Objects.equals(e.codigo, codigo)) {
                return e;
            }
        }
        throw new RecursoNoEncontradoException("Código de estado de reservación no válido: " + codigo);
    }
}