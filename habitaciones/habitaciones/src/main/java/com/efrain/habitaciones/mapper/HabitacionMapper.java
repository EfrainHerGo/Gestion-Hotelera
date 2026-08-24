package com.efrain.habitaciones.mapper;

import com.efrain.Common.dto.HabitacionRequest;
import com.efrain.Common.dto.HabitacionResponse;
import com.efrain.Common.mapper.CommonMapper;
import com.efrain.habitaciones.entity.Habitaciones;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitaciones> {

    @Override
    public Habitaciones requestAEntidad(HabitacionRequest request) {
        return Habitaciones.builder()
                .numeroHabitacion(request.numeroHabitacion())
                .tipo(request.tipo())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .build();
    }

    @Override
    public HabitacionResponse entidadResponse(Habitaciones entidad) {
        return new HabitacionResponse(
                entidad.getNumeroHabitacion(),
                entidad.getTipo(),
                entidad.getPrecio(),
                entidad.getCapacidad()
        );
    }
}