package com.efrain.Common.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "habitaciones")
public interface habitacionClient {
}
