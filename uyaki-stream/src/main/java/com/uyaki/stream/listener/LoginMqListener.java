package com.uyaki.stream.listener;

import com.uyaki.stream.base.config.Sink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * The type Login listener.
 *
 * @date 2020 /10/14
 */
@Slf4j
@Service
public class LoginMqListener {
    /**
     * Receive logout msg.
     *
     * @param message the message
     */
    @StreamListener(value = Sink.LOGOUT_INPUT, condition = "headers['version']=='1.0'")
    public void receiveLogoutMsg(@Payload String message) {
        String msg = "LoginMqListener: " + message;
        log.error(msg);
        throw new RuntimeException("Message consumer failed!");
    }
}
