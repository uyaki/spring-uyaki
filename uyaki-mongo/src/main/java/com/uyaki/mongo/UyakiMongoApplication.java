package com.uyaki.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * The type Uyaki mongo application.
 *
 * @date 2020 /07/17
 */
@EnableOpenApi
@SpringBootApplication
public class UyakiMongoApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UyakiMongoApplication.class, args);
    }

}
