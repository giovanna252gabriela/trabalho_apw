package com.example.tabelafipe.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableMongoRepositories("com.example.tabelafipe.repository")
public class TabelafipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabelafipeApplication.class, args);
	}

}
