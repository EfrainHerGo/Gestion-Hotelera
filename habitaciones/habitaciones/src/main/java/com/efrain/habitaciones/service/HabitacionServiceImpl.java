package com.efrain.habitaciones.service;

import com.efrain.Common.dto.Habitacion.HabitacionRequest;
import com.efrain.Common.dto.Habitacion.HabitacionResponse;
import com.efrain.Common.enums.EstadoHabitacion;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.exceptions.RecursoNoEncontradoException;
import com.efrain.habitaciones.entity.Habitaciones;
import com.efrain.habitaciones.mapper.HabitacionMapper;
import com.efrain.habitaciones.repository.HabitacionesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionesRepository habitacionesRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Buscando habitaciones activas...");

        return habitacionesRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(habitacionMapper::entidadResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Buscando habitación {} activa...", id);

        return habitacionMapper.entidadResponse(
                obtenerHabitacionActivaOrExcep(id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id) {
        log.info("Buscando habitación {} sin estado...", id);

        return habitacionMapper.entidadResponse(
                habitacionesRepository.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException(
                                "Habitación no encontrada con el id " + id
                        ))
        );
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando habitación...");

        validarDatosUnicos(request);

        Habitaciones habitacion = habitacionMapper.requestAEntidad(request);

        habitacion.establecerEstadoInicial();

        habitacionesRepository.save(habitacion);

        log.info(
                "Habitación {} registrada",
                habitacion.getNumeroHabitacion()
        );

        return habitacionMapper.entidadResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {

        Habitaciones habitacion = obtenerHabitacionActivaOrExcep(id);

        validarCambiosUnicos(request, id);

        habitacion.actualizarHabitacion(
                request.numeroHabitacion(),
                request.tipo(),
                request.precio(),
                request.capacidad()
        );

        log.info("Registro actualizado de la habitación {}", id);

        return habitacionMapper.entidadResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        Habitaciones habitacion = obtenerHabitacionActivaOrExcep(id);
        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA) {
            throw new IllegalStateException(
                    "No se puede eliminar una habitación OCUPADA"
            );
        }
        habitacion.eliminar();
        log.info("Habitación eliminada");
    }
    @Override
    public void cambiarEstado(Long id, Long idEstado) {

        Habitaciones habitacion = obtenerHabitacionActivaOrExcep(id);

        log.info(
                "Cambiando estado de Habitacion {} a {}...",
                id,
                idEstado
        );

        EstadoHabitacion nuevoEstado =
                EstadoHabitacion.obtenerEstadoHabitacionPorCodigo(idEstado);

        EstadoHabitacion estadoAnterior =
                habitacion.getEstadoHabitacion();

        habitacion.cambiarEstado(nuevoEstado);

        log.info(
                "Estado de la habitación {} cambiado de {} a {}",
                id,
                estadoAnterior,
                nuevoEstado
        );
    }
    private Habitaciones obtenerHabitacionActivaOrExcep(Long id) {
        log.info("Buscando habitación con id {}", id);

        return habitacionesRepository
                .findByIdHabitacionesAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró la habitación activa con id " + id
                        )
                );
    }

    private void validarDatosUnicos(HabitacionRequest request) {

        log.info("Validando información única...");

        log.info("Número de habitación...");

        if (habitacionesRepository.existsByNumeroHabitacionAndEstadoRegistro(
                request.numeroHabitacion(),
                EstadoRegistro.ACTIVO
        )) {
            throw new IllegalArgumentException(
                    "Ya existe una habitación activa con el número ingresado"
            );
        }
    }

    private void validarCambiosUnicos(
            HabitacionRequest request,
            Long id
    ) {

        log.info("Validando información única de la habitación...");

        log.info("Número de habitación a actualizar...");

        if (habitacionesRepository
                .existsByNumeroHabitacionAndEstadoRegistroAndIdHabitacionesNot(
                        request.numeroHabitacion(),
                        EstadoRegistro.ACTIVO,
                        id
                )) {
            throw new IllegalArgumentException(
                    "Ya existe una habitación activa con el número ingresado"
            );
        }
    }
}