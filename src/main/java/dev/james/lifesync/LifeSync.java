package dev.james.lifesync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.james.lifesync.*")
public class LifeSync {
    public static void main(String[] args) {
        SpringApplication.run(LifeSync.class, args);
    }
}
