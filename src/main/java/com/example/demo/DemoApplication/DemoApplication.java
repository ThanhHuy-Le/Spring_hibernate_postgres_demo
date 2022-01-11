package com.example.demo.DemoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication // meta-annotation that pulls in component scanning, autoconfiguration, and property support.
public class DemoApplication {

	// https://spring.io/guides/tutorials/rest/ REST tutorial on spring
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
