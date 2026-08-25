package com.efrain.reservasHoteleras.entities;

import com.efrain.reservasHoteleras.enums.EstadoReservacion;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVACION")
    Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    LocalDateTime fechaIngreso;

    @Column(name = "FECHA_SALIDA", nullable = false)
    LocalDateTime fechaSalida;

    @Column(name = "ESTADO_RESERVACION", nullable = false)
    EstadoReservacion estadoReservacion;

}
