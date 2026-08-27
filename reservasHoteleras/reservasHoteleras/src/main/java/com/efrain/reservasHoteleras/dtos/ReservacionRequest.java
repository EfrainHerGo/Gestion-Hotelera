package com.efrain.reservasHoteleras.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ReservacionRequest(
        @NotNull(message = "El id del huesped es requerido")
        @Positive(message = "El id del huesped debe ser positivo")
        Long idHuesped,

        @NotNull(message = "El id del la habitacion es requerido")
        @Positive(message = "El id del habitacion debe ser positivo")
        Long idHabitacion,

        @NotNull(message = "La fecha de entrada es requerida")
        @FutureOrPresent(message = "La fecha de la reservacion debe ser futura o de hoy")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaEntrada,

        @NotNull(message = "La fecha de salida es requerida")
        @FutureOrPresent(message = "La fecha de la reservacion debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaSalida


) {
}
