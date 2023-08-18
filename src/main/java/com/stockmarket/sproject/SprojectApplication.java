package com.stockmarket.sproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprojectApplication.class, args);
	}

}
