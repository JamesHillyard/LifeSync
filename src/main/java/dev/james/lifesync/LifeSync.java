package dev.james.lifesync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "dev.james.lifesync.*")
@ServletComponentScan
public class LifeSync {
    public static void main(String[] args) {
        SpringApplication.run(LifeSync.class, args);
    }
}
