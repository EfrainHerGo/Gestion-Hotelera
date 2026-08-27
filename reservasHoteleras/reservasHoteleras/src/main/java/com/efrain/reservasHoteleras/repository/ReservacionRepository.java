package com.efrain.reservasHoteleras.repository;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.reservasHoteleras.entities.Reservacion;
import com.efrain.reservasHoteleras.enums.EstadoReservacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    boolean existsByIdHuespedAndEstadoReservacionIn(Long idHuesped, List<EstadoReservacion> estadoReservacions);

    boolean existsByIdHuespedAndEstadoReservacionInAndIdNot(Long idHuesped, List<EstadoReservacion> estadoReservacions, Long id);
}