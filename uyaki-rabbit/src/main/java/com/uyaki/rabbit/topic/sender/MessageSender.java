package com.uyaki.rabbit.topic.sender;

import com.uyaki.rabbit.common.model.dto.Msg;

/**
 * The interface Message sender.
 *
 * @author uyaki
 * @date 2020 /06/22
 */
public interface MessageSender {
    /**
     * 发送交互消息
     *
     * @param routingKey the routing key
     * @param msg        the msg
     */
    void sendMsg(String routingKey, Msg msg);
}
