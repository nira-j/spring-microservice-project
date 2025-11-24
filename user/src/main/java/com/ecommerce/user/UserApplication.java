package com.ecommerce.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {
	
	 @Bean
	 public OpenAPI customOpenAPI() {
	     return new OpenAPI()
            .info(new Info()
            .title("My API Documentation")
            .version("1.0")
            .description("Sample description of API endpoints"));
	   }

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
