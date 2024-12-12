/*
 * Copyright (c) ICANIO
 */

package com.SalesForce;

import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "SalesForce API", version = "1.0", description = "SalesForce Information"))
public class SalesForceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesForceApplication.class, args);

		System.out.println("**********************************************");
		System.out.println("********** Enter SalesForce Application ******");
		System.out.println("**********************************************");

	}


}
