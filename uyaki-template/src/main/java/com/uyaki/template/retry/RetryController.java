package com.uyaki.template.retry;

import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @date 2020/08/21
 */
@RestController
public class RetryController {

    @Resource
    private RetryService retryService;

    @GetMapping("/retry")
    public int retryMethod( ) throws Exception {
        return retryService.retryMethod(0);
    }
}
