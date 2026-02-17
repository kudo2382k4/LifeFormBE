package com.fpt.edu.lifeform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LifeformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeformApplication.class, args);
	}

}
