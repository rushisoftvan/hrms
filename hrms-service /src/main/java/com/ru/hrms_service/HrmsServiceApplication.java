package com.ru.hrms_service;

import com.ru.hrms_service.holiday.entities.HolidayEntity;
import com.ru.hrms_service.holiday.repositories.HolidayRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class HrmsServiceApplication {

	private final HolidayRepo holidayRepo;

	private static List<String> leakyList = new ArrayList<>();

	public static void main(String[] args) {
	   	SpringApplication.run(HrmsServiceApplication.class, args);
//		int numberOfThreads = 100;
//
//		for (int i = 0; i < numberOfThreads; i++) {
//			Thread thread = new Thread(new LeakyWorker(i));
//			thread.start();
//		}
//		HrmsServiceApplication leak = new HrmsServiceApplication();
		//leak.startLeaking();


	}



@GetMapping("/fetch")
	public List<HolidayEntity> fetch(){
		return holidayRepo.findAll();
	}

	}



