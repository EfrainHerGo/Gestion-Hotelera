package com.efrain.Common.dto.Huesped;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 50 caracteres")
        String nombre,
        @NotBlank(message = "El apellido paterno es requerido")
        @Size(min = 5, max = 50, message = "El apellido paterno debe tener entre 5 y 50 caracteres")
        String apellidoPaterno,
        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 5, max = 50, message = "El apellido materno debe tener entre 5 y 50 caracteres")
        String apellidoMaterno,

        @NotBlank(message = "El correo es requerido")
        @Size(min = 5, max = 100, message = "El corre debe tener entre 5 y 100 caracteres")
        String email,
        @NotBlank(message = "El telefono es requerido")
        @Size(min = 5, max = 10, message = "El telefono debe tener 10 caracteres")
        String telefono,
        @NotBlank(message = "El documento es requerida")
        @Size(min = 7, max = 20, message = "EL documento debe tener exacatemnte 20 caracteres")
        String documento,
        @NotBlank(message = "La nacionalidad es requerida")
        @Size(min = 5, max = 12, message = "La nacionadlidad debe tener exacatemnte 12 caracteres")
        String nacionalidad


) {
}
