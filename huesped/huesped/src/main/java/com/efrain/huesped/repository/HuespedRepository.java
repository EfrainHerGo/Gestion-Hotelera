package com.efrain.huesped.repository;

import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.huesped.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);

    boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estadoRegistro, Long id);
    boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estadoRegistro, Long id);



}
