package carzanodev.genuniv.microservices.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GeneralInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralInfoServiceApplication.class, args);
    }

}
