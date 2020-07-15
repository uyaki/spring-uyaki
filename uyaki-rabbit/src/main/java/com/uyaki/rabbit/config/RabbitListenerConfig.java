package com.uyaki.rabbit.config;

import com.uyaki.rabbit.common.constant.ExchangeNameConstants;
import com.uyaki.rabbit.common.constant.RoutingKeyConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The type Rabbit listener config.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
@Configuration
public class RabbitListenerConfig {
    /**
     * The Amqp admin.
     */
    @Resource
    AmqpAdmin amqpAdmin;

    /**
     * The constant QUEUE_DIG.
     */
    public static final String QUEUE_USER = "uyaki.topic.queue.user";
    /**
     * The constant QUEUE_USER.
     */
    public static final String QUEUE_DIG = "uyaki.topic.queue.dig";
    /**
     * The constant QUEUE_WATCH.
     */
    public static final String QUEUE_WATCH = "uyaki.topic.queue.watch";

    /**
     * Post create queue.
     */
    @PostConstruct
    public void postCreateQueue() {
        TopicExchange exchange = new TopicExchange(ExchangeNameConstants.TOPIC_EXCHANGE_ACTION);
        amqpAdmin.declareExchange(exchange);

        Queue queueDig = new Queue(RabbitListenerConfig.QUEUE_DIG);
        amqpAdmin.declareQueue(queueDig);
        Binding queueDigBind = BindingBuilder
                .bind(queueDig)
                .to(exchange)
                .with(RoutingKeyConstants.TOPIC_RECEIVED_ROUTING_DIG);
        amqpAdmin.declareBinding(queueDigBind);

        Queue queueWatch = new Queue(RabbitListenerConfig.QUEUE_WATCH);
        amqpAdmin.declareQueue(queueWatch);
        Binding queueWatchBind = BindingBuilder
                .bind(queueWatch)
                .to(exchange)
                .with(RoutingKeyConstants.TOPIC_RECEIVED_ROUTING_WATCH);
        amqpAdmin.declareBinding(queueWatchBind);

        Queue queueUser = new Queue(RabbitListenerConfig.QUEUE_USER);
        amqpAdmin.declareQueue(queueUser);
        Binding queueUserBind = BindingBuilder
                .bind(queueUser)
                .to(exchange)
                .with(RoutingKeyConstants.TOPIC_RECEIVED_ROUTING_USER);
        amqpAdmin.declareBinding(queueUserBind);
    }

}
