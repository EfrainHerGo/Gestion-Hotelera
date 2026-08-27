package com.efrain.reservasHoteleras.mappers;

import com.efrain.Common.dto.Habitacion.DatosHabitacion;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.dto.Huesped.DatosHuesped;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.mapper.CommonMapper;
import com.efrain.reservasHoteleras.dtos.ReservacionRequest;
import com.efrain.reservasHoteleras.dtos.ReservacionResponse;
import com.efrain.reservasHoteleras.entities.Reservacion;
import org.springframework.stereotype.Component;


@Component
public class ReservacionMapper implements CommonMapper<ReservacionRequest, ReservacionResponse, Reservacion> {
    @Override
    public Reservacion requestAEntidad(ReservacionRequest request) {
        if (request == null) return null;
        return Reservacion.crear(
                request.idHuesped(),
                request.idHabitacion(),
                request.fechaEntrada(),
                request.fechaSalida()
        );
    }

    @Override
    public ReservacionResponse entidadResponse(Reservacion entidad) {
        if (entidad == null) return null;
        return new ReservacionResponse(
                entidad.getId(),
                null,
                null,
                entidad.getFechaIngreso(),
                entidad.getFechaSalida(),
                entidad.getEstadoReservacion(),
                entidad.getEstadoRegistro());
    }

    public ReservacionResponse entidadResponse(Reservacion entidad, HuespedResponse huespedResponse, HabitacionResponse habitacionResponse) {
        if (entidad == null) return null;
        return new ReservacionResponse(
                entidad.getId(),
                huespedResponseADatosHuesped(huespedResponse),
                habitacionResponseADatosResponse(habitacionResponse),
                entidad.getFechaIngreso(),
                entidad.getFechaSalida(),
                entidad.getEstadoReservacion(),
                entidad.getEstadoRegistro());
    }

    public DatosHuesped huespedResponseADatosHuesped(HuespedResponse huesped){
        if (huesped == null) return null;
        return new DatosHuesped(
                huesped.nombre(),
                huesped.email(),
                huesped.telefono(),
                huesped.documento(),
                huesped.nacionalidad()
        );
    }

    private DatosHabitacion habitacionResponseADatosResponse(HabitacionResponse habitacion){
        if (habitacion == null) return null;
        return new DatosHabitacion(
                habitacion.numeroHabitacion(),
                habitacion.tipo(),
                habitacion.precio()
        );
    }

}
