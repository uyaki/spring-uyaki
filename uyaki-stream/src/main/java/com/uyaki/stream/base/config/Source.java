package com.uyaki.stream.base.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 消息发送通道
 *
 * @date 2020 /10/13
 */
public interface Source {
    /**
     * 通道名 与yml配置文件对应
     * spring.cloud.stream.bindings.[login_output].*
     */
    String LOGIN_OUTPUT = "login_output";
    /**
     * 通道名 与yml配置文件对应
     * spring.cloud.stream.bindings.[logout_output].*
     * The constant OUTPUT.
     */
    String LOGOUT_OUTPUT = "logout_output";

    /**
     * Message message channel.
     *
     * @return the message channel
     */
    @Output(LOGIN_OUTPUT)
    MessageChannel loginOutputMessageChannel();


    /**
     * Message message channel.
     *
     * @return the message channel
     */
    @Output(LOGOUT_OUTPUT)
    MessageChannel logoutOutputMessageChannel();
}
