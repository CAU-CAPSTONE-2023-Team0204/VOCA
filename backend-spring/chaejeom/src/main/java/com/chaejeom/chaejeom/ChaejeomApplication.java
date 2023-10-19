package com.chaejeom.chaejeom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChaejeomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaejeomApplication.class, args);
	}

}
