package com.clinic.ms_pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsPagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPagosApplication.class, args);
    }
}