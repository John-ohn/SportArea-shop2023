package com.sportArea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sportArea.*"})
public class SportAreaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportAreaApplication.class, args);
	}

}
