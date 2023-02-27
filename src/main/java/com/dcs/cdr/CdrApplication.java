package com.dcs.cdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.dcs.cdr")
public class CdrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdrApplication.class, args);
	}

}
