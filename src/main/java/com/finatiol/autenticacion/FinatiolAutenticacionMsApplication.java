package com.finatiol.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinatiolAutenticacionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinatiolAutenticacionMsApplication.class, args);
	}

}
