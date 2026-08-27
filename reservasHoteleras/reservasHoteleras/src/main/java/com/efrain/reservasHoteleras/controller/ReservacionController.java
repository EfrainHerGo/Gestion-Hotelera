package com.efrain.reservasHoteleras.controller;

import com.efrain.Common.controllers.CommonController;
import com.efrain.reservasHoteleras.dtos.ReservacionRequest;
import com.efrain.reservasHoteleras.dtos.ReservacionResponse;
import com.efrain.reservasHoteleras.service.ReservacionService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ReservacionController extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {

    public ReservacionController(ReservacionService service) {
        super(service);
    }

    @GetMapping("/huesped/{idHuesped}/en-curso")
    public ResponseEntity<Boolean> tieneReservasEnCursoPorHuespedId(
            @PathVariable("idHuesped") @Positive(message = "El id del huésped debe ser positivo") Long idHuesped) {
        return ResponseEntity.ok(service.existeReservacionActivaPorHuesped(idHuesped));
    }

    @GetMapping("/habitacion/{idHabitacion}/en-curso")
    public ResponseEntity<Boolean> tieneReservasEnCursoPorHabitacionId(
            @PathVariable("idHabitacion") @Positive(message = "El id de la habitación debe ser positivo") Long idHabitacion) {
        return ResponseEntity.ok(service.existeReservacionActivaPorHabitacion(idHabitacion));
    }

    @PatchMapping("/{id}/estado/{codigoEstado}")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable("id") @Positive Long id,
            @PathVariable("codigoEstado") @NotNull Long codigoEstado) {
        service.actualizarEstadoReservacion(id, codigoEstado);
        return ResponseEntity.noContent().build();
    }
}