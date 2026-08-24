package com.efrain.Common.dto;
import java.math.BigDecimal;

public record HabitacionResponse(
        Integer numeroHabitacion,
        String tipo,
        BigDecimal precio,
        Integer capacidad
) {
}