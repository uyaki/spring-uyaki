# uyaki-rabbit

## bind 如下

|Exchange| Routing key|Queue|
|:----:|:----:|:----:|
|uyaki.topic.exchange.action|uyaki.topic.routing.user.*	|uyaki.topic.queue.user|
|uyaki.topic.exchange.action|uyaki.topic.routing.*.dig|uyaki.topic.queue.dig|
|uyaki.topic.exchange.action|uyaki.topic.routing.*.watch|uyaki.topic.queue.watch|

## Sender

```java
/**
 * The constant TOPIC_SENDER_ROUTING_USER_DIG.
 */
public static final String TOPIC_SENDER_ROUTING_USER_DIG = "uyaki.topic.routing.user.dig";
/**
 * The constant TOPIC_SENDER_ROUTING_USER_WATCH.
 */
public static final String TOPIC_SENDER_ROUTING_USER_WATCH = "uyaki.topic.routing.user.watch";
```

```java
@Test
void sendDig() {
    Msg dig = Msg.builder().msg("gb-dig").build();
    messageSender.sendMsg(RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_DIG, dig);
    System.out.println(String.format("sender发送消息：%s -> %s 成功", RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_DIG, dig.getMsg()));
}
@Test
void sendWatch(){
    Msg watch = Msg.builder().msg("gb-watch").build();
    messageSender.sendMsg(RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_WATCH, watch);
    System.out.println(String.format("sender发送消息：%s -> %s 成功", RoutingKeyConstants.TOPIC_SENDER_ROUTING_USER_WATCH, watch.getMsg()));
}
```

## Listener
```java
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
```