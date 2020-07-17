package com.uyaki.swagger3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * The type Uyaki swagger 3 application.
 *
 * @date 2020 /07/17
 */
@EnableOpenApi
@SpringBootApplication
public class UyakiSwagger3Application {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UyakiSwagger3Application.class, args);
    }

}
