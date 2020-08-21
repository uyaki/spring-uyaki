package com.uyaki.template;

import com.uyaki.template.retry.RetryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = UyakiTemplateApplicationTests.class)
@RunWith(SpringRunner.class)
@SpringBootApplication
class UyakiTemplateApplicationTests {

    @Resource
    private RetryService retryService;

    @Test
    public void t1() throws Exception {
        retryService.retryMethod(0);

        Thread.sleep(10000);
        System.out.println("sb");
    }

}
