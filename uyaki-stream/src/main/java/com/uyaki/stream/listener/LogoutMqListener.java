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
public class LogoutMqListener {
    /**
     * Receive login msg.
     *
     * @param message the message
     */
    @StreamListener(value = Sink.LOGIN_INPUT, condition = "headers['version']=='1.0'")
    public void receiveLoginMsg(@Payload String message) {
        String msg = "LogoutMqListener: " + message;
        log.error(msg);
        throw new RuntimeException("Message consumer failed!");
    }
}
