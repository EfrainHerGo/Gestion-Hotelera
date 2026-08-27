package com.efrain.reservasHoteleras.service;

import com.efrain.Common.client.HabitacionClient;
import com.efrain.Common.client.HuespedClient;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.exceptions.RecursoNoEncontradoException;
import com.efrain.reservasHoteleras.dtos.ReservacionRequest;
import com.efrain.reservasHoteleras.dtos.ReservacionResponse;
import com.efrain.reservasHoteleras.entities.Reservacion;
import com.efrain.reservasHoteleras.enums.EstadoReservacion;
import com.efrain.reservasHoteleras.mappers.ReservacionMapper;
import com.efrain.reservasHoteleras.repository.ReservacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservacionServiceImpl implements ReservacionService{
    @Override
    public ReservacionResponse obtenerreservacionPorIdSinEstado(Long id) {
        return null;
    }

    private final ReservacionRepository reservacionRepository;
    private final ReservacionMapper reservacionMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;
    private static final List<EstadoReservacion> ESTADO_RESERVACION_ACTIVA = List.of(
            EstadoReservacion.CONFIRMADA,
            EstadoReservacion.EN_CURSO
    );


    @Override
    public void actualizarEstadoReservacion(Long idReservacion, Long idEstadoReservacion) {

    }

    @Override
    public void existeReservacionActivaPorHuesped(Long idHuesped) {

    }

    @Override
    public void existeReservacionActivaPorHabitacion(Long idHabitacion) {

    }

    @Override
    public List<ReservacionResponse> listar() {
        log.info("Listado de todas las reservaciones");
        return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(reservacionMapper::entidadResponse)
                .toList();
                       /* obtenerHuespedActivo(reservacion.getIdHuesped()),
                        obtenerHAbitacionActiva(reservacion.getIdHabitacion()))).toList();*/

    }

    @Override
    public ReservacionResponse obtenerPorId(Long id) {
        Reservacion reservacion = obtenerReservacionException(id);
        return reservacionMapper.entidadResponse(
                reservacion,
                obtenerHuespedActivo(reservacion.getIdHuesped()),
                obtenerHAbitacionActiva(reservacion.getIdHabitacion())
        );
    }

    @Override
    public ReservacionResponse registrar(ReservacionRequest request) {
        log.info("Registrando las reservaciones");
        HabitacionResponse habitacion = obtenerHAbitacionActiva(request.idHabitacion());
        HuespedResponse huesped = obtenerHuespedActivo(request.idHuesped());

        Reservacion reservacion = Reservacion.crear(
                request.idHuesped(),
                request.idHabitacion(),
                request.fechaEntrada(),
                request.fechaSalida()
        );
        Reservacion reservacionGuardada = reservacionRepository.save(reservacion);

        log.info("Reservacion Registrada");
        return reservacionMapper.entidadResponse(reservacionGuardada, huesped, habitacion);
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        Reservacion reservacion = obtenerReservacionException(id);
        obtenerHuespedActivo(reservacion.getIdHuesped());
        //consultareservacionesActivasPorReservacion
        reservacion.actualizar(
                request.idHuesped(),
                request.idHabitacion()
        );


        return reservacionMapper.entidadResponse(
                reservacion,
                obtenerHuespedActivo(reservacion.getIdHuesped()),
                obtenerHAbitacionActiva(reservacion.getIdHabitacion())
        );
    }

    @Override
    public void eliminar(Long id) {
        Reservacion reservacion = obtenerReservacionException(id);
        log.info("Eliminando estado de reservacion {}", id);
        reservacion.eliminar();
        log.info("Cita elimanada con exito");
    }
    private Reservacion obtenerReservacionException(Long id){
        return reservacionRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Reservacion no encontrada"));
    }

    private HuespedResponse obtenerHuespedActivo(Long id){
        return huespedClient.obtenerHuespedPorId(id);
    }
    private HabitacionResponse obtenerHAbitacionActiva(Long id){
        return habitacionClient.obtenerHabitacionPorId(id);
    }

}
