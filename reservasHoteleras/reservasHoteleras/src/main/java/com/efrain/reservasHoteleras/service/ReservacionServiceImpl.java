package com.efrain.reservasHoteleras.service;

import com.efrain.Common.client.HabitacionClient;
import com.efrain.Common.client.HuespedClient;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.enums.EstadoHabitacion;
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
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservacionServiceImpl implements ReservacionService {

    private final ReservacionRepository reservacionRepository;
    private final ReservacionMapper reservacionMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;

    private static final List<EstadoReservacion> ESTADOS_ACTIVOS = List.of(
            EstadoReservacion.CONFIRMADA,
            EstadoReservacion.EN_CURSO
    );

    @Override
    @Transactional(readOnly = true)
    public ReservacionResponse obtenerreservacionPorIdSinEstado(Long id) {
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reservación no encontrada con ID: " + id));
        return enriquecerRespuesta(reservacion);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeReservacionActivaPorHuesped(Long idHuesped) {
        return reservacionRepository.existsByIdHuespedAndEstadoReservacionIn(idHuesped, ESTADOS_ACTIVOS);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeReservacionActivaPorHabitacion(Long idHabitacion) {
        return reservacionRepository.existsByIdHabitacionAndEstadoReservacionIn(idHabitacion, ESTADOS_ACTIVOS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservacionResponse> listar() {
        log.info("Listando todas las reservaciones activas en el registro");
        return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(this::enriquecerRespuesta)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservacionResponse obtenerPorId(Long id) {
        Reservacion reservacion = obtenerReservacionActivaOException(id);
        return enriquecerRespuesta(reservacion);
    }

    @Override
    public ReservacionResponse registrar(ReservacionRequest request) {
        log.info("Registrando nueva reservación para el huésped ID: {}", request.idHuesped());

        HuespedResponse huesped = huespedClient.obtenerHuespedPorId(request.idHuesped());

        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.idHabitacion());

        Reservacion reservacion = Reservacion.crear(
                request.idHuesped(),
                request.idHabitacion(),
                request.fechaEntrada(),
                request.fechaSalida()
        );

        Reservacion guardada = reservacionRepository.save(reservacion);

        habitacionClient.cambiarEstado(request.idHabitacion(), EstadoHabitacion.OCUPADA.getCodigo());

        log.info("Reservación con ID {} creada con éxito", guardada.getId());
        return reservacionMapper.entidadResponse(guardada, huesped, habitacion);
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        Reservacion reservacion = obtenerReservacionActivaOException(id);
        log.info("Actualizando reservación ID: {} en estado {}", id, reservacion.getEstadoReservacion());

        if (reservacion.getEstadoReservacion() == EstadoReservacion.FINALIZADA ||
                reservacion.getEstadoReservacion() == EstadoReservacion.CANCELADA) {
            throw new IllegalStateException("No se permiten modificaciones en reservaciones FINALIZADAS o CANCELADAS");
        }

        if (reservacion.getEstadoReservacion() == EstadoReservacion.CONFIRMADA) {
            if (!Objects.equals(reservacion.getIdHabitacion(), request.idHabitacion())) {
                HabitacionResponse nuevaHabitacion = habitacionClient.obtenerHabitacionPorId(request.idHabitacion());

                habitacionClient.cambiarEstado(reservacion.getIdHabitacion(), EstadoHabitacion.DISPONIBLE.getCodigo());
                habitacionClient.cambiarEstado(request.idHabitacion(), EstadoHabitacion.OCUPADA.getCodigo());
            }

            huespedClient.obtenerHuespedPorId(request.idHuesped());
            reservacion.actualizarConfirmada(
                    request.idHuesped(),
                    request.idHabitacion(),
                    request.fechaEntrada(),
                    request.fechaSalida()
            );
        } else if (reservacion.getEstadoReservacion() == EstadoReservacion.EN_CURSO) {
            if (!Objects.equals(reservacion.getIdHuesped(), request.idHuesped()) ||
                    !Objects.equals(reservacion.getIdHabitacion(), request.idHabitacion()) ||
                    !Objects.equals(reservacion.getFechaIngreso(), request.fechaEntrada())) {
                throw new IllegalStateException("Con Check-in realizado (EN_CURSO), solo se permite modificar la fecha de salida");
            }
            reservacion.actualizarFechaSalidaEnCurso(request.fechaSalida());
        }

        return enriquecerRespuesta(reservacion);
    }

    @Override
    public void actualizarEstadoReservacion(Long idReservacion, Long codigoEstadoReservacion) {
        Reservacion reservacion = obtenerReservacionActivaOException(idReservacion);
        EstadoReservacion nuevoEstado = EstadoReservacion.obtenerEstadoReservacionPorCodigo(codigoEstadoReservacion);

        log.info("Cambiando estado de reservación {} de {} a {}", idReservacion, reservacion.getEstadoReservacion(), nuevoEstado);

        reservacion.cambiarEstado(nuevoEstado);

        if (nuevoEstado == EstadoReservacion.FINALIZADA || nuevoEstado == EstadoReservacion.CANCELADA) {
            habitacionClient.cambiarEstado(reservacion.getIdHabitacion(), EstadoHabitacion.DISPONIBLE.getCodigo());
        }
    }

    @Override
    public void eliminar(Long id) {
        Reservacion reservacion = obtenerReservacionActivaOException(id);
        log.info("Cancelando la reservación ID: {}", id);

        reservacion.cancelar();
        habitacionClient.cambiarEstado(reservacion.getIdHabitacion(), EstadoHabitacion.DISPONIBLE.getCodigo());
        reservacion.eliminar();
    }

    private Reservacion obtenerReservacionActivaOException(Long id) {
        return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reservación activa no encontrada con ID: " + id));
    }

    private ReservacionResponse enriquecerRespuesta(Reservacion reservacion) {
        HuespedResponse huesped = huespedClient.obtenerHuespedPorId(reservacion.getIdHuesped());
        HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(reservacion.getIdHabitacion());
        return reservacionMapper.entidadResponse(reservacion, huesped, habitacion);
    }
    private Long obtenerNuevoEstadoHabitacion(Long idEstadoReservacion) {
        EstadoReservacion estadoReservacion = EstadoReservacion.obtenerEstadoReservacionPorCodigo(idEstadoReservacion);
        return switch (estadoReservacion) {
            case CONFIRMADA, EN_CURSO -> EstadoHabitacion.OCUPADA.getCodigo();
            case FINALIZADA, CANCELADA -> EstadoHabitacion.DISPONIBLE.getCodigo();
        };
    }

}