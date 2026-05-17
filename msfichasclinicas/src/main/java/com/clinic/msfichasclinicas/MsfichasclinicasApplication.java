package com.clinic.msfichasclinicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsfichasclinicasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfichasclinicasApplication.class, args);
    }

}