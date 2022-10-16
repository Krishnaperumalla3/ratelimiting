package ratelimiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RatelimitingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatelimitingApplication.class, args);
	}

}
