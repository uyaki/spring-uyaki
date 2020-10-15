package com.uyaki.stream.base.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Routing key properties.
 *
 * @date 2020 /10/15
 */
@Component
@Getter
public class RoutingKeyProperties {
    @Value("${spring.cloud.stream.rabbit.bindings.login_input.consumer.binding-routing-key}")
    private String loginRoutingKey;
    @Value("${spring.cloud.stream.rabbit.bindings.logout_input.consumer.binding-routing-key}")
    private String logoutRoutingKey;
}
