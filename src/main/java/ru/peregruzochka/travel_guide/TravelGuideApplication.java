package ru.peregruzochka.travel_guide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class TravelGuideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelGuideApplication.class, args);
	}

}
