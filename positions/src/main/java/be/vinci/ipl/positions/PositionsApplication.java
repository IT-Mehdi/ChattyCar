package be.vinci.ipl.positions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PositionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PositionsApplication.class, args);
    }

}
