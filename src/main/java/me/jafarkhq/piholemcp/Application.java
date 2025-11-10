package me.jafarkhq.piholemcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystemProperty;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Workaround: Disable console logging
        System.setProperty(LoggingSystemProperty.CONSOLE_THRESHOLD.getEnvironmentVariableName(), "OFF");

        SpringApplication.run(Application.class, args);
    }

}
