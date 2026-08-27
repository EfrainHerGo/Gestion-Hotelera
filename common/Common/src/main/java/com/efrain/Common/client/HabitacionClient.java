package com.efrain.Common.client;

import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.enums.EstadoHabitacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "habitaciones")
public interface HabitacionClient {
    @GetMapping("/{id}")
    HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);

    @GetMapping("/id-habitacion/{id}")
    HabitacionResponse cambiarEstado(@PathVariable Long id, Long idEstado);
}
