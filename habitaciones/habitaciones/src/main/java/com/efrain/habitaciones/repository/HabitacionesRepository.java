package com.efrain.habitaciones.repository;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.habitaciones.entity.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionesRepository extends JpaRepository<Habitaciones, Long> {
    List<Habitaciones> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Habitaciones> findByIdHabitacionesAndEstadoRegistro(
            Long idHabitaciones,
            EstadoRegistro estadoRegistro
    );    boolean existsByNumeroHabitacionAndEstadoRegistro(Integer numeroHabitacion, EstadoRegistro estadoRegistro);
    boolean existsByNumeroHabitacionAndEstadoRegistroAndIdHabitacionesNot(Integer numeroHabitacion, EstadoRegistro estadoRegistro, Long idHabitaciones);
}