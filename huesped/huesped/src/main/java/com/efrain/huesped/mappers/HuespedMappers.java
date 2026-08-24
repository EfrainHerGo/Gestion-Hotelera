package com.efrain.huesped.mappers;

import com.efrain.Common.mapper.CommonMapper;
import com.efrain.huesped.entities.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMappers implements CommonMapper {
    @Override
    public Object requestAEntidad(Object request) {
        if (request == null) return null;
        return Huesped.builder()
                .nombre(request.no)
    }

    @Override
    public Object entidadResponse(Object entidad) {
        return null;
    }
}
