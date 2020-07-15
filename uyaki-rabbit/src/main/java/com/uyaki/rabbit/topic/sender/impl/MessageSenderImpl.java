package com.uyaki.rabbit.topic.sender.impl;

import com.alibaba.fastjson.JSON;
import com.uyaki.rabbit.common.constant.ExchangeNameConstants;
import com.uyaki.rabbit.common.model.dto.Msg;
import com.uyaki.rabbit.topic.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Message sender.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
@Slf4j
@Component
public class MessageSenderImpl implements MessageSender {
    /**
     * The Rabbit messaging template.
     */
    final RabbitMessagingTemplate rabbitMessagingTemplate;

    /**
     * Instantiates a new Message sender.
     *
     * @param rabbitMessagingTemplate the rabbit messaging template
     */
    public MessageSenderImpl(RabbitMessagingTemplate rabbitMessagingTemplate) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    @Override
    public void sendMsg(String routingKey, Msg msg) {
        assert msg != null;
        String message = JSON.toJSONString(msg);
        try {
            rabbitMessagingTemplate.convertAndSend(ExchangeNameConstants.TOPIC_EXCHANGE_ACTION, routingKey, message);
        } catch (Exception e) {
            log.error("send InteractMsg {} error", message);
        }
    }
}
