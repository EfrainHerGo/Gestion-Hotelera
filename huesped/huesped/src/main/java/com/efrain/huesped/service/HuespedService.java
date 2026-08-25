package com.efrain.huesped.service;

import com.efrain.Common.dto.Huesped.HuespedRequest;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.Common.enums.EstadoRegistro;
import com.efrain.Common.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse obtenerHuespedPorIdSinEstado(Long id);


}
