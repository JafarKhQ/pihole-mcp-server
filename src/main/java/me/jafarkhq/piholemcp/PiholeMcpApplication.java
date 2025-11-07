package me.jafarkhq.piholemcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class PiholeMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiholeMcpApplication.class, args);
    }

}
