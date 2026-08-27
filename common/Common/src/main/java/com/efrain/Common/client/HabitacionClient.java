package com.efrain.Common.client;

import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.enums.EstadoHabitacion;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "habitaciones")
public interface HabitacionClient {
    @GetMapping("/{id}")
    HabitacionResponse obtenerPorId(@PathVariable Long id);

    @PutMapping("/{idHabitacion}/estado/{idEstado}")
    ResponseEntity<Void> cambiarEstado( @PathVariable @Positive(message = "El id habitacion debe ser psoitivo") Long idHabitacion,
                                       @PathVariable @Positive(message = "El idEstado debe ser psoitivo")Long idEstado);
}
