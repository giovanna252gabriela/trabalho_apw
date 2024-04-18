package com.example.climaBD.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableMongoRepositories("com.example.climaBD.repository")
public class ClimaBdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimaBdApplication.class, args);
	}

}
