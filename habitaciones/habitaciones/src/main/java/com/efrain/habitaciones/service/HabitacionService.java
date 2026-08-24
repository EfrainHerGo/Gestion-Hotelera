package com.efrain.habitaciones.service;

import com.efrain.Common.dto.HabitacionRequest;
import com.efrain.Common.dto.HabitacionResponse;
import com.efrain.Common.services.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {

    HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id);

}