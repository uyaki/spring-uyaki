package com.uyaki.rabbit.common.constant;

/**
 * The type Routing key constants.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
public class RoutingKeyConstants {
    /**
     * The constant TOPIC_SENDER_ROUTING_USER_DIG.
     */
    public static final String TOPIC_SENDER_ROUTING_USER_DIG = "uyaki.topic.routing.user.dig";
    /**
     * The constant TOPIC_SENDER_ROUTING_USER_WATCH.
     */
    public static final String TOPIC_SENDER_ROUTING_USER_WATCH = "uyaki.topic.routing.user.watch";
    /**
     * The constant TOPIC_RECEIVED_ROUTING_USER.
     */
    public static final String TOPIC_RECEIVED_ROUTING_USER = "uyaki.topic.routing.user.*";
    /**
     * The constant TOPIC_RECEIVED_ROUTING_DIG.
     */
    public static final String TOPIC_RECEIVED_ROUTING_DIG = "uyaki.topic.routing.*.dig";
    /**
     * The constant TOPIC_RECEIVED_ROUTING_WATCH.
     */
    public static final String TOPIC_RECEIVED_ROUTING_WATCH = "uyaki.topic.routing.*.watch";
}
