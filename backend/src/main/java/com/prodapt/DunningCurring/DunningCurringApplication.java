package com.prodapt.DunningCurring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DunningCurringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DunningCurringApplication.class, args);
	}

}
