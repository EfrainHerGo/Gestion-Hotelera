package com.efrain.huesped;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication(scanBasePackages = {"com.efrain.huesped", "com.efrain.Common"})
@EnableFeignClients
public class HuespedApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedApplication.class, args);
	}

}
