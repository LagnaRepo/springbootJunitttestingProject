package com.yash.springdatajparestful;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="Spring Boot Rest Api Documentation ",
				description = "Spring Boot Rest Api Documentation  Demo",
				version = "V-1.0",
				contact = @Contact(
						name="Lagnajita",
						email ="lagnajita.saha@yash.com",
						url="http://localhost:8080/api/user"
				)
		)
)
public class SpringdatajparestfulwebservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringdatajparestfulwebservicesApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return  new ModelMapper();
	}

}
