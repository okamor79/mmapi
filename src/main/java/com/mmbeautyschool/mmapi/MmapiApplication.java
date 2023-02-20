package com.mmbeautyschool.mmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MmapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmapiApplication.class, args);
    }

}
