package com.uyaki.rabbit.topic.listener;

import com.alibaba.fastjson.JSON;
import com.uyaki.rabbit.common.model.dto.Msg;
import com.uyaki.rabbit.config.RabbitListenerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * The type User dig listener.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
@Component
@Slf4j
public class UserListener {
    /**
     * Process post create msg.
     *
     * @param message the message
     */
    @RabbitListener(queues = RabbitListenerConfig.QUEUE_USER)
    public void processPostCreateMsg(String message) {
        Msg msg = JSON.parseObject(message, Msg.class);
        try {
            System.out.println(String.format("USER-Listener收到消息：%s", msg.getMsg()));
        } catch (Exception e) {
            log.error("msg consume error : {} {}", msg, e);
        }
    }
}
