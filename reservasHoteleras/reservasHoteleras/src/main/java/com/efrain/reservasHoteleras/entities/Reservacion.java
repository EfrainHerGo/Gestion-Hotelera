package com.efrain.reservasHoteleras.entities;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.utils.StringCustomUtils;
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
    Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    Long idHuesped;

    @Column(name = "ID_HABITACION", nullable = false)
    Long idHabitacion;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    LocalDateTime fechaIngreso;

    @Column(name = "FECHA_SALIDA", nullable = false)
    LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVACION", nullable = false)
    EstadoReservacion estadoReservacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    EstadoRegistro estadoRegistro;

    public static void validarDatos(Long idHuesped, Long idHabitacion){
        validarId(idHuesped, "Huesped");
        validarId(idHabitacion, "Habiatacion");
/*        validarFechaIngreso(fechaIngreso, fechaSalida);
        validarFechaSalida(fechaSalida, fechaSalida);
*/    }
    private static void validarFechaIngreso(LocalDateTime fechaIngreso,LocalDateTime fechaSalida){
        if (fechaIngreso == null || fechaSalida.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("LA fecha de la cita es requerida y debe ser fututra");
    }
    private static void validarFechaSalida(LocalDateTime fechaIngreso,LocalDateTime fechaSalida){
        if (fechaIngreso == null || fechaSalida.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("LA fecha de la cita es requerida y debe ser fututra");
    }
    private static void validarId(Long id, String campo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El " + campo + " debe ser un valor positivo");
        }
    }

    public static Reservacion crear(Long idHuesped, Long idHabitacion,
                                    LocalDateTime fechaIngreso, LocalDateTime fechaSalida) {
        validarDatos(idHuesped, idHabitacion);
        return Reservacion.builder()
                .idHuesped(idHuesped)
                .idHabitacion(idHabitacion)
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .estadoReservacion(EstadoReservacion.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO) // <-- Agregar estadoRegistro
                .build();
    }
    public void eliminar(){
        //validarActualizacionPermitida();
        this.estadoReservacion = estadoReservacion.CANCELADA;
    }
    public void actualizar(
            Long idHuesped, Long idHabitacion
    ) {
        validarDatos(idHuesped, idHabitacion);
        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
    }
}
