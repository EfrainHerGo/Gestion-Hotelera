package com.efrain.reservasHoteleras.entities;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.reservasHoteleras.enums.EstadoReservacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVACION")
    private Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVACION", nullable = false)
    private EstadoReservacion estadoReservacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private EstadoRegistro estadoRegistro;

    public static void validarDatos(Long idHuesped, Long idHabitacion, LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        validarId(idHuesped, "Huésped");
        validarId(idHabitacion, "Habitación");
        validarFechas(fechaIngreso, fechaSalida);
    }

    private static void validarFechas(LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        if (fechaIngreso == null || fechaSalida == null) {
            throw new IllegalArgumentException("Las fechas de entrada y salida son obligatorias");
        }
        if (!fechaIngreso.isBefore(fechaSalida)) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

    private static void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El " + campo + " debe ser un valor positivo");
        }
    }

    public static Reservacion crear(Long idHuesped, Long idHabitacion,
                                    LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        validarDatos(idHuesped, idHabitacion, fechaIngreso, fechaSalida);
        return Reservacion.builder()
                .idHuesped(idHuesped)
                .idHabitacion(idHabitacion)
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .estadoReservacion(EstadoReservacion.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    public void cambiarEstado(EstadoReservacion nuevoEstado) {
        if (!this.estadoReservacion.puedeCambiarA(nuevoEstado)) {
            throw new IllegalStateException(
                    String.format("Transición de estado no válida: de %s a %s", this.estadoReservacion, nuevoEstado)
            );
        }
        this.estadoReservacion = nuevoEstado;
    }

    public void actualizarConfirmada(Long idHuesped, Long idHabitacion, LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        if (this.estadoReservacion != EstadoReservacion.CONFIRMADA) {
            throw new IllegalStateException("Solo se pueden actualizar todos los datos si la reserva está CONFIRMADA");
        }
        validarDatos(idHuesped, idHabitacion, fechaIngreso, fechaSalida);
        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    public void actualizarFechaSalidaEnCurso(LocalDateTime nuevaFechaSalida) {
        if (this.estadoReservacion != EstadoReservacion.EN_CURSO) {
            throw new IllegalStateException("Únicamente se puede actualizar la fecha de salida cuando la reserva está EN_CURSO");
        }
        if (nuevaFechaSalida == null || !this.fechaIngreso.isBefore(nuevaFechaSalida)) {
            throw new IllegalStateException("La nueva fecha de salida debe ser posterior a la fecha de entrada");
        }
        this.fechaSalida = nuevaFechaSalida;
    }

    public void cancelar() {
        if (this.estadoReservacion != EstadoReservacion.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede cancelar una reservación en estado CONFIRMADA");
        }
        this.estadoReservacion = EstadoReservacion.CANCELADA;
    }

    public void eliminar() {
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }
}