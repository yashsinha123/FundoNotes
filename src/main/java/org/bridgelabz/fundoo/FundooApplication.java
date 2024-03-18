package org.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;

@SpringBootApplication
public class FundooApplication {

    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        SpringApplication.run(FundooApplication.class, args);
    }

}
