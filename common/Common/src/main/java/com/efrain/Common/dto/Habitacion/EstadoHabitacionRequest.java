        package com.efrain.Common.dto.Habitacion;

        import com.efrain.Common.enums.EstadoHabitacion;
        import jakarta.validation.constraints.NotNull;

        public record EstadoHabitacionRequest(

                @NotNull(message = "El estado es requerido")
                EstadoHabitacion estadoHabitacion

        ) {
        }
