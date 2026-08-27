package com.efrain.Common.dto.Habitacion;

import com.efrain.Common.enums.EstadoHabitacion;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record HabitacionRequest(


        @NotNull(message = "Es requerido")
        @Positive(message = "El número de habitación debe ser mayor a 0")
        Integer numeroHabitacion,

        @NotBlank(message = "Es requerido")
        @Size(min = 1, max = 50, message = "Debe contener entre 1 y 50 caracteres")
        String tipo,

        @NotNull(message = "Es requerido")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "Es requerido")
        @Min(value = 1, message = "La capacidad mínima es de 1")
        Integer capacidad,

        @NotNull(message = "El estado es requerido")
        EstadoHabitacion estadoHabitacion

) {
}