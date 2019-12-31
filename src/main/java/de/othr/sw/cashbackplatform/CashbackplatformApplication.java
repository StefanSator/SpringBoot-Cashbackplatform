package de.othr.sw.cashbackplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CashbackplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashbackplatformApplication.class, args);
	}

}
