package com.efrain.huesped.service;

import com.efrain.Common.client.ReservacionClient;
import com.efrain.Common.dto.Huesped.HuespedRequest;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.exceptions.RecursoNoEncontradoException;

import com.efrain.huesped.entities.Huesped;
import com.efrain.huesped.mappers.HuespedMappers;
import com.efrain.huesped.repository.HuespedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final HuespedMappers huespedMappers;
    private final ReservacionClient reservaClient;

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerHuespedPorIdSinEstado(Long id) {
        log.info("Obteniendo huésped por ID sin validar estado registro: {}", id);
        return huespedRepository.findById(id)
                .map(huespedMappers::entidadResponse)
                .orElseThrow(() -> new IllegalArgumentException("Huésped no encontrado con el id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        log.info("Obteniendo listado de huéspedes activos");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(huespedMappers::entidadResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Obteniendo huésped activo por id: {}", id);
        return huespedMappers.entidadResponse(obtenerHuespedActivoOException(id));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Registrando nuevo huésped: {}", request.email());
        validarDatosUnicos(request);

        Huesped huesped = huespedMappers.requestAEntidad(request);
        huespedRepository.save(huesped);

        log.info("Huésped registrado con éxito con ID: {}", huesped.getId());
        return huespedMappers.entidadResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = obtenerHuespedActivoOException(id);
        log.info("Actualizando información del huésped con ID: {}", id);

        validarCambiosUnicos(request, id);
        huesped.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono(),
                request.documento(),
                request.nacionalidad()
        );

        log.info("Huésped con ID {} actualizado correctamente", id);
        return huespedMappers.entidadResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = obtenerHuespedActivoOException(id);
        log.info("Procesando eliminación lógica del huésped con ID: {}", id);

        boolean tieneReservasEnCurso = reservaClient.tieneReservasEnCursoPorHabitacionId(id);
        if (tieneReservasEnCurso) {
            throw new IllegalStateException("No se puede eliminar el huésped porque posee reservas EN_CURSO");
        }

        huesped.eliminar();
        log.info("Huésped con ID {} eliminado con éxito", id);
    }

    private Huesped obtenerHuespedActivoOException(Long id) {
        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huésped activo no encontrado con id: " + id));
    }

    private void validarDatosUnicos(HuespedRequest request) {
        log.info("Validando datos únicos del huésped");

        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email().trim(), EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("Ya existe un huésped activo con el correo: " + request.email());
        }

        if (huespedRepository.existsByTelefonoAndEstadoRegistro(request.telefono().trim(), EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("Ya existe un huésped activo con el teléfono: " + request.telefono());
        }

        if (huespedRepository.existsByDocumentoAndEstadoRegistro(request.documento().trim(), EstadoRegistro.ACTIVO)) {
            throw new IllegalArgumentException("Ya existe un huésped activo con el documento: " + request.documento());
        }
    }

    private void validarCambiosUnicos(HuespedRequest request, Long id) {
        log.info("Validando datos únicos para actualización de huésped ID: {}", id);

        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(request.email().trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalArgumentException("Ya existe otro huésped activo con el correo: " + request.email());
        }

        if (huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(request.telefono().trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalArgumentException("Ya existe otro huésped activo con el teléfono: " + request.telefono());
        }

        if (huespedRepository.existsByDocumentoAndEstadoRegistroAndIdNot(request.documento().trim(), EstadoRegistro.ACTIVO, id)) {
            throw new IllegalArgumentException("Ya existe otro huésped activo con el documento: " + request.documento());
        }
    }
}