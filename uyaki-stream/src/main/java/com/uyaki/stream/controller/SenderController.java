package com.uyaki.stream.controller;

import com.uyaki.stream.base.config.RoutingKeyProperties;
import com.uyaki.stream.base.config.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The type Hello controller.
 *
 * @date 2020 /10/13
 */
@Slf4j
@RestController
@RequestMapping("")
public class SenderController {

    @Resource
    private Source source;

    @Resource
    private RoutingKeyProperties routingKeyProperties;

    /**
     * Send succeed.
     */
    @GetMapping("/send")
    public void sendSucceed() {
        source.loginOutputMessageChannel().send(MessageBuilder.withPayload("Login...")
                // 指定接收者的routingKey
                .setHeader("routingKey", routingKeyProperties.getLoginRoutingKey())
                // 过滤条件，按需添加
                .setHeader("version", "1.0")
                // 需要配置 delayed-exchange: true
//                .setHeader("x-delay", 5000)
                .build());
        source.logoutOutputMessageChannel().send(MessageBuilder.withPayload("Logout...")
                // 指定接收者的routingKey
                .setHeader("routingKey", routingKeyProperties.getLogoutRoutingKey())
                // 过滤条件，按需添加
                .setHeader("version", "1.0")
                // 需要配置 delayed-exchange: true
//                .setHeader("x-delay", 5000)
                .build());
    }
}
