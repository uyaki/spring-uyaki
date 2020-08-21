package com.uyaki.template.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * The type Retry service.
 *
 * @date 2020 /08/21
 */
@Slf4j
@Service
public class RetryService {
    /**
     * Retry method int.
     *
     * @param code the code
     * @return the int
     * @throws Exception the exception
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public int retryMethod(int code) throws Exception {
        log.info("start retryMethod => " + LocalTime.now());
        log.info("currentThread Name => " + Thread.currentThread().getName());
        if (code == 0) {
            throw new Exception("fuck!!！");
        }
        System.out.println("nice!!！");

        return 200;
    }
}
