package com.example.p_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class PProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PProjectApplication.class, args);
	}

}
