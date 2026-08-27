package com.efrain.habitaciones.entity;

import com.efrain.Common.enums.EstadoHabitacion;
import com.efrain.Common.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "HABITACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Habitaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACIONES")
    private Long idHabitaciones;

    @Column(name = "NUMERO_HABITACION", nullable = false)
    private Integer numeroHabitacion;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", nullable = false, length = 20)
    private EstadoHabitacion estadoHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 10)
    private EstadoRegistro estadoRegistro;

    public void actualizarHabitacion(
            Integer numeroHabitacion,
            String tipo,
            BigDecimal precio,
            Integer capacidad
    ) {
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo.trim();
        this.precio = precio;
        this.capacidad = capacidad;
    }

    public void eliminar() {
        this.estadoRegistro = EstadoRegistro.ELIMINADO;
    }
    public void establecerEstadoInicial() {
        this.estadoHabitacion = EstadoHabitacion.DISPONIBLE;
        this.estadoRegistro = EstadoRegistro.ACTIVO;
    }
    public void cambiarEstado(EstadoHabitacion nuevoEstado) {

        if (this.estadoHabitacion == EstadoHabitacion.OCUPADA
                && nuevoEstado == EstadoHabitacion.DISPONIBLE) {

            throw new IllegalStateException(
                    "No se puede cambiar manualmente una habitación OCUPADA a DISPONIBLE"
            );
        }

        this.estadoHabitacion = nuevoEstado;
    }
}