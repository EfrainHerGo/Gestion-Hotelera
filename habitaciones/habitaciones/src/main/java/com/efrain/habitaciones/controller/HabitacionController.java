package com.efrain.habitaciones.controller;

import com.efrain.Common.controllers.CommonController;
import com.efrain.Common.dto.Habitacion.HabitacionRequest;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.habitaciones.service.HabitacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class HabitacionController
        extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(
            @PathVariable
            @Positive(message = "Id debe ser valor positivo")
            Long id
    ) {
        return ResponseEntity.ok(service.obtenerHabitacionPorIdSinEstado(id));
    }
}