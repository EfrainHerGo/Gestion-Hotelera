package com.efrain.reservasHoteleras.controller;

import com.efrain.Common.controllers.CommonController;
import com.efrain.Common.services.CrudService;
import com.efrain.reservasHoteleras.dtos.ReservacionRequest;
import com.efrain.reservasHoteleras.dtos.ReservacionResponse;
import com.efrain.reservasHoteleras.service.ReservacionService;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ReservacionController
        extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {
    public ReservacionController(ReservacionService service) {
        super(service);
    }
    public ResponseEntity<ReservacionResponse> obtenterReservacionPorIdSinEstado(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ){
        return ResponseEntity.ok(service.obtenerreservacionPorIdSinEstado(id));
    }
}
