package com.vivek.batvball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application-{spring.profiles.active}.properties")
@SpringBootApplication
public class BatvballApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatvballApplication.class, args);
	}

}
