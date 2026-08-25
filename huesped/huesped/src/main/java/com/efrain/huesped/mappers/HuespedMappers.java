package com.efrain.huesped.mappers;

import com.efrain.Common.dto.Huesped.HuespedRequest;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.mapper.CommonMapper;
import com.efrain.huesped.entities.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMappers implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {
    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        if (request == null )return null;
        return Huesped.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().trim())
                .telefono(request.telefono().trim())
                .documento(request.documento().trim())
                .nacionalidad(request.nacionalidad())
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public HuespedResponse entidadResponse(Huesped entidad) {
        if (entidad == null )return null;
        return new HuespedResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                entidad.getDocumento(),
                entidad.getNacionalidad()
        );
   }
}
