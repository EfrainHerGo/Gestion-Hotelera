package com.efrain.Common.mapper;


public interface CommonMapper<RQ, RS, E> {
    E requestAEntidad(RQ request);
    RS entidadResponse(E entidad);

}
