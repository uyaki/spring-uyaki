package com.uyaki.stream.base.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息接收通道
 *
 * @date 2020 /10/13
 */
public interface Sink {
    /**
     * 通道名 与yml配置文件对应
     * spring.cloud.stream.bindings.[login_input].*
     * The constant INPUT.
     */
    String LOGIN_INPUT = "login_input";

    /**
     * 通道名 与yml配置文件对应
     * spring.cloud.stream.bindings.[logout_input].*
     * The constant INPUT_2.
     */
    String LOGOUT_INPUT = "logout_input";

    /**
     * Sub 1 subscribable channel.
     *
     * @return the subscribable channel
     */
    @Input(LOGIN_INPUT)
    SubscribableChannel loginInputChannel();

    /**
     * Sub 2 subscribable channel.
     *
     * @return the subscribable channel
     */
    @Input(LOGOUT_INPUT)
    SubscribableChannel logoutInputChannel();
}
