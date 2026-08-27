package com.efrain.reservasHoteleras.dtos;

import com.efrain.Common.dto.Habitacion.DatosHabitacion;
import com.efrain.Common.dto.Huesped.DatosHuesped;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.reservasHoteleras.enums.EstadoReservacion;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservacionResponse(
        Long id,
        DatosHuesped huesped,
        DatosHabitacion habitacion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy HH:mm")
        LocalDateTime fechaEntrada,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy HH:mm")
        LocalDateTime fechaSalida,
        EstadoReservacion estadoReservacion,
        EstadoRegistro estadoRegistro
        ) {
}
