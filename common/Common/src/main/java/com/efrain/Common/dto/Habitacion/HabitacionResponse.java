package com.efrain.Common.dto.Habitacion;
import com.efrain.Common.enums.EstadoHabitacion;

import java.math.BigDecimal;

public record HabitacionResponse(
        Long idHabitaciones,
        Integer numeroHabitacion,
        String tipo,
        BigDecimal precio,
        Integer capacidad

) {
}