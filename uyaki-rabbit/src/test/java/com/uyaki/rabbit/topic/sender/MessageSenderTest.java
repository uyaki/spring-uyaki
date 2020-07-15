package com.uyaki.rabbit.topic.sender;

import com.uyaki.rabbit.common.model.dto.Msg;
import com.uyaki.rabbit.common.constant.RoutingKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * The type Message sender test.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
@SpringBootTest(classes = MessageSenderTest.class)
@RunWith(SpringRunner.class)
@SpringBootApplication
@Slf4j
class MessageSenderTest {

    @Resource
    private MessageSender messageSender;

    /**
     * Send msg.
     */
    @Test
    void sendDig() {
        Msg dig = Msg.builder().msg("gb-dig").build();
        messageSender.sendMsg(RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_DIG, dig);
        System.out.println(String.format("sender发送消息：%s -> %s 成功", RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_DIG, dig.getMsg()));
    }

    /**
     * Send watch.
     */
    @Test
    void sendWatch(){
        Msg watch = Msg.builder().msg("gb-watch").build();
        messageSender.sendMsg(RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_WATCH, watch);
        System.out.println(String.format("sender发送消息：%s -> %s 成功", RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_WATCH, watch.getMsg()));
    }
}