package com.uyaki.hello;

import com.uyaki.exception.common.UnifiedServiceExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The type Uyaki hello application.
 */
@SpringBootApplication
public class UyakiHelloApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UyakiHelloApplication.class, args);
    }

    /**
     * Unified service exception handler unified service exception handler.
     *
     * @return the unified service exception handler
     */
    @Bean
    public void serviceExceptionHandler() {
    }
}
