package com.maison.mabs.sotd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SotdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SotdApplication.class, args);
	}

}
