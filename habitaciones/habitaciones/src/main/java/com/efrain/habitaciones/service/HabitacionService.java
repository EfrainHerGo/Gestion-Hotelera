package com.efrain.habitaciones.service;

import com.efrain.Common.dto.Habitacion.HabitacionRequest;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.services.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {

    HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id);

    void cambiarEstado(Long id, Long idEstado);
}