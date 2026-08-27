package com.efrain.Common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservasHoteleras")
public interface ReservacionClient {
    @GetMapping("/api/reservaciones/huesped/{idHuesped}/en-curso")
    Boolean tieneReservasEnCursoPorHuespedId(@PathVariable("idHuesped") Long idHuesped);

    @GetMapping("/api/reservaciones/habitacion/{idHabitacion}/en-curso")
    Boolean tieneReservasEnCursoPorHabitacionId(@PathVariable("idHabitacion") Long idHabitacion);

}
