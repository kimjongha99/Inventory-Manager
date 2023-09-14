package com.springboot.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryApplication {

    //주석

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
