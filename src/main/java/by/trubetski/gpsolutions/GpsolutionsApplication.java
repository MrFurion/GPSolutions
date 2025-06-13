package by.trubetski.gpsolutions;

import by.trubetski.gpsolutions.env.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("dev")
public class GpsolutionsApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnv();
		SpringApplication.run(GpsolutionsApplication.class, args);
	}

}
