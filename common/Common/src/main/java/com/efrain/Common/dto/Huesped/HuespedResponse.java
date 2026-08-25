package com.efrain.Common.dto.Huesped;

import com.efrain.Common.enums.EstadoRegistro;

public record HuespedResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String nacionalidad


) {
}
