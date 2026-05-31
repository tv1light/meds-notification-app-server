package ru.tv1light.medsnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedsNotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedsNotificationApplication.class, args);
    }
}
