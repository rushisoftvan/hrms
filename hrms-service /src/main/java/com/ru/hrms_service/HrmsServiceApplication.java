package com.ru.hrms_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class HrmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(
			HrmsServiceApplication.class, args);

	}

}
