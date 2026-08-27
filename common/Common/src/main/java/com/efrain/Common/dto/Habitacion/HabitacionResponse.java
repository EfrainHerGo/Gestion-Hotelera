package com.efrain.Common.dto.Habitacion;
import java.math.BigDecimal;

public record HabitacionResponse(
        Integer numeroHabitacion,
        String tipo,
        BigDecimal precio,
        Integer capacidad
) {
}