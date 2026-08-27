package com.efrain.Common.client;

import com.efrain.Common.dto.Huesped.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "huesped")
public interface HuespedClient {
    @GetMapping("/{id}")
    HuespedResponse obtenerHuespedPorId(@PathVariable Long id);

    @GetMapping("/id-huesped/{id}")
    HuespedResponse obtenerHuespedPorIdSinEstado(@PathVariable Long id);
}
