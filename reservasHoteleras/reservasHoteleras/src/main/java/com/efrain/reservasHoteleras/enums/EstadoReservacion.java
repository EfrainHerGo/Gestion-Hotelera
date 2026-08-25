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

    CONFIRMADA(2L, "Confirmada por el paciente", true, false) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return EnumSet.of(EN_CURSO, CANCELADA);
        }
    },
    EN_CURSO(3L, "Paciente en consulta", true, false) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {

            return EnumSet.of(FINALIZADA, CANCELADA);

        }
    },
    FINALIZADA(4L, "Cita finalizada", false, true) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return Set.of();
        }
    },
    CANCELADA(5L, "Cita cancelada", false, true) {
        @Override
        public Set<EstadoReservacion> puedeCambiar() {
            return Set.of();
        }
    };
    private final Long codigo;
    private final String descripcion;
    private final boolean actualizable;
    private final boolean eliminable;


    public abstract Set<EstadoReservacion> puedeCambiar();

    public boolean puedeCambiarA(EstadoReservacion nuevoEstado){
        return puedeCambiar().contains(nuevoEstado);
    }

    public static EstadoReservacion obtenerEstadoReservacionPorCodigo(Long codigo){
        for (EstadoReservacion e : values()){
            if (Objects.equals(e.codigo, codigo))
                return e;
        }
        throw new RecursoNoEncontradoException("Codigo de disponibildad no valido: " + codigo);
    }
}
