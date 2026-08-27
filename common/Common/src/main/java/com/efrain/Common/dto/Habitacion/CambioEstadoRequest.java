package com.efrain.Common.dto.Habitacion;

import com.efrain.Common.enums.EstadoHabitacion;

public record CambioEstadoRequest(
        EstadoHabitacion estado
) {
}
