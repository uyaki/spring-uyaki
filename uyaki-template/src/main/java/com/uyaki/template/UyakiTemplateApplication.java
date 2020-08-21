package com.uyaki.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class UyakiTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(UyakiTemplateApplication.class, args);
    }

}
