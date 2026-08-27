package com.efrain.huesped.controller;

import com.efrain.Common.controllers.CommonController;
import com.efrain.Common.dto.Huesped.HuespedRequest;
import com.efrain.Common.dto.Huesped.HuespedResponse;
import com.efrain.huesped.service.HuespedService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public HuespedController(HuespedService service) {
        super(service);
    }


    public ResponseEntity<HuespedResponse> obtenerHuespedPoridSinEstado(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ){
        return  ResponseEntity.ok(service.obtenerHuespedPorIdSinEstado(id));
    }



}

