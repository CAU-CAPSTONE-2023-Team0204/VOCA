package com.chaejeom.chaejeom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EntityScan(basePackages = {"com.chaejeom.chaejeom.domain"})
@EnableJpaAuditing
@SpringBootApplication
public class ChaejeomApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChaejeomApplication.class, args);
	}

}
