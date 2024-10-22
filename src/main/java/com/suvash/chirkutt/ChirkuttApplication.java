package com.suvash.chirkutt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChirkuttApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChirkuttApplication.class, args);

		System.out.println("System Running at http://127.0.0.1:8080");
	}

}
