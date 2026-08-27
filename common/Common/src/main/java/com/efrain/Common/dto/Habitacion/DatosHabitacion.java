package com.efrain.Common.dto.Habitacion;

import java.math.BigDecimal;

public record DatosHabitacion(
        Integer numeroHabitacion,
        String tipo,
        BigDecimal precio
) {
}
