package com.sparta.vroomvroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VroomVroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(VroomVroomApplication.class, args);
	}

}
