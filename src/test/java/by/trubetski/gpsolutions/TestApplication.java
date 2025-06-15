package by.trubetski.gpsolutions;


import by.trubetski.gpsolutions.env.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        EnvLoader.loadEnv();
        SpringApplication.run(TestApplication.class, args);
    }
}
