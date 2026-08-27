package com.efrain.reservasHoteleras.service;

import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.services.CrudService;
import com.efrain.reservasHoteleras.dtos.ReservacionRequest;
import com.efrain.reservasHoteleras.dtos.ReservacionResponse;

public interface ReservacionService extends CrudService<ReservacionRequest, ReservacionResponse> {
    ReservacionResponse obtenerreservacionPorIdSinEstado(Long id);
    void actualizarEstadoReservacion(Long idReservacion, Long idEstadoReservacion);
    void existeReservacionActivaPorHuesped(Long idHuesped);
    void existeReservacionActivaPorHabitacion(Long idHabitacion);
}
