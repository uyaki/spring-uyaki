package com.uyaki.rabbit.config;

import com.uyaki.rabbit.common.constant.ExchangeNameConstants;
import com.uyaki.rabbit.topic.sender.MessageSender;
import com.uyaki.rabbit.topic.sender.impl.MessageSenderImpl;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * The type Rabbit sender config.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
@Configuration
public class RabbitSenderConfig {
    /**
     * Init exchange topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    TopicExchange initExchange() {
        return new TopicExchange(ExchangeNameConstants.TOPIC_EXCHANGE_ACTION);
    }

    /**
     * Message sender message sender.
     *
     * @param rabbitMessagingTemplate the rabbit messaging template
     * @return the message sender
     */
    @Bean
    MessageSender messageSender(RabbitMessagingTemplate rabbitMessagingTemplate) {
        return new MessageSenderImpl(rabbitMessagingTemplate);
    }
}
