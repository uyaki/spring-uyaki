package com.uyaki.redis.sentinel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The type Uayaki redis sentinel application tests.
 */
@SpringBootTest(classes = UayakiRedisSentinelApplicationTests.class)
@RunWith(SpringRunner.class)
@SpringBootApplication
@Slf4j
class UayakiRedisSentinelApplicationTests {

    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
    }

}
