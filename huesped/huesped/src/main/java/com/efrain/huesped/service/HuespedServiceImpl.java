package com.efrain.huesped.service;

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


    @Override
    public HuespedResponse obtenerHuespedPorIdSinEstado(Long id) {
        return null;
    }

    @Override
    public List<HuespedResponse> listar() {
        log.info("Listado de huespedes");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(huespedMappers::entidadResponse).toList();
    }

    @Override
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Huespe por id");
        return huespedMappers.entidadResponse(huespedRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huesped con el id {} no encontrado" + id )));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
       log.info("Registrar al medico");
       validarDatosUnicos(request);
       Huesped huesped = huespedMappers.requestAEntidad(request);
       huespedRepository.save(huesped);
       log.info("Registrado con exito");
        return huespedMappers.entidadResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = obtenerHuespedActivoOException(id);
        log.info("Actualizar al huesped");
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
        log.info("Huesped actualizado correctamente");
        return huespedMappers.entidadResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = obtenerHuespedActivoOException(id);
        log.info("Eliminación del huesped");
        huesped.eliminar();
        log.info("Huesped Elimnado con exito");


    }

    private Huesped obtenerHuespedActivoOException(Long id){
        log.info("Buscando huesped activos");
        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huesped activo no encontrado con id: " + id));
    }

    private void validarDatosUnicos(HuespedRequest request){
        log.info("Validando email unico");
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(request.email().trim(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya doctor con ese correo: " + request.email());
        log.info("Validar el telefo");
        if(huespedRepository.existsByTelefonoAndEstadoRegistro(request.telefono().trim(), EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un usuario con ese telefono" + request.telefono());
    }

    private void validarCambiosUnicos(HuespedRequest request, Long id){
        log.info("Validando email unico para actualizar");
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(request.email().trim(), EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya doctor con ese correo: " + request.email());
        log.info("Validar el telefo para actualizar");
        if(huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(request.telefono().trim(), EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un usuario con ese telefono" + request.telefono());
    }
}
