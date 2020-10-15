# uyaki-stream

## rabbitMQ部署
[docker-compose脚本](https://github.com/uyaki/docker-script/blob/master/rabbitmq/docker-compose.yml)

## 引入包
```gradle
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-starter-stream-rabbit:3.0.8.RELEASE'
}
```
## 使用

1. 声名`Sink`和`Source`

```java
public interface Sink {
    String LOGIN_INPUT = "login_input";
    String LOGOUT_INPUT = "logout_input";

    @Input(LOGIN_INPUT)
    SubscribableChannel loginInputChannel();

    @Input(LOGOUT_INPUT)
    SubscribableChannel logoutInputChannel();
}
```

```java
public interface Source {
    String LOGIN_OUTPUT = "login_output";
    String LOGOUT_OUTPUT = "logout_output";
    
    @Output(LOGIN_OUTPUT)
    MessageChannel loginOutputMessageChannel();

    @Output(LOGOUT_OUTPUT)
    MessageChannel logoutOutputMessageChannel();
}
```

```java
@EnableBinding(value = {Source.class, Sink.class})
public class StreamAutoConfiguration {
}
```

2. 设置配置文件，并读取

```properties
# rabbit
spring.cloud.stream.binders.windrabbit.type=rabbit
spring.cloud.stream.binders.windrabbit.environment.spring.rabbitmq.addresses=localhost
spring.cloud.stream.binders.windrabbit.environment.spring.rabbitmq.port=15672
spring.cloud.stream.binders.windrabbit.environment.spring.rabbitmq.username=user
spring.cloud.stream.binders.windrabbit.environment.spring.rabbitmq.password=password
# login config
spring.cloud.stream.bindings.login_output.destination=login-user
spring.cloud.stream.bindings.login_output.binder=windrabbit
spring.cloud.stream.bindings.login_input.destination=login-user
spring.cloud.stream.bindings.login_input.group=logined-member
spring.cloud.stream.bindings.login_input.consumer.max-attempts=1
spring.cloud.stream.bindings.login_input.binder=windrabbit
spring.cloud.stream.rabbit.bindings.login_output.producer.routing-key-expression=headers.routingKey
spring.cloud.stream.rabbit.bindings.login_input.consumer.binding-routing-key=login.user.succeed
spring.cloud.stream.rabbit.bindings.login_input.consumer.auto-bind-dlq=true
spring.cloud.stream.rabbit.bindings.login_input.consumer.republish-to-dlq=true
# logout config
spring.cloud.stream.bindings.logout_output.destination=logout-user
spring.cloud.stream.bindings.logout_output.binder=windrabbit
spring.cloud.stream.bindings.logout_input.destination=logout-user
spring.cloud.stream.bindings.logout_input.group=logout-member
spring.cloud.stream.bindings.logout_input.consumer.max-attempts=1
spring.cloud.stream.bindings.logout_input.binder=windrabbit
spring.cloud.stream.rabbit.bindings.logout_output.producer.routing-key-expression=headers.routingKey
spring.cloud.stream.rabbit.bindings.logout_input.consumer.binding-routing-key=logout.user.succeed
spring.cloud.stream.rabbit.bindings.logout_input.consumer.auto-bind-dlq=true
spring.cloud.stream.rabbit.bindings.logout_input.consumer.republish-to-dlq=true
```
```java
@Component
@Getter
public class RoutingKeyProperties {
    @Value("${spring.cloud.stream.rabbit.bindings.login_input.consumer.binding-routing-key}")
    private String loginRoutingKey;
    @Value("${spring.cloud.stream.rabbit.bindings.logout_input.consumer.binding-routing-key}")
    private String logoutRoutingKey;
}
```
3. 发送消息

```java
@Slf4j
@RestController
@RequestMapping("")
public class SenderController {

    @Resource
    private Source source;

    @Resource
    private RoutingKeyProperties routingKeyProperties;

    /**
     * Send succeed.
     */
    @GetMapping("/send")
    public void sendSucceed() {
        source.loginOutputMessageChannel().send(MessageBuilder.withPayload("Login...")
                // 指定接收者的routingKey
                .setHeader("routingKey", routingKeyProperties.getLoginRoutingKey())
                // 过滤条件，按需添加
                .setHeader("version", "1.0")
                // 需要配置 delayed-exchange: true
//                .setHeader("x-delay", 5000)
                .build());
        source.logoutOutputMessageChannel().send(MessageBuilder.withPayload("Logout...")
                // 指定接收者的routingKey
                .setHeader("routingKey", routingKeyProperties.getLogoutRoutingKey())
                // 过滤条件，按需添加
                .setHeader("version", "1.0")
                // 需要配置 delayed-exchange: true
//                .setHeader("x-delay", 5000)
                .build());
    }
}
```

4. 消息监听

```java
@Slf4j
@Service
public class LoginMqListener {
    @StreamListener(value = Sink.LOGOUT_INPUT, condition = "headers['version']=='1.0'")
    public void receiveLogoutMsg(@Payload String message) {
        String msg = "LoginMqListener: " + message;
        log.error(msg);
        throw new RuntimeException("Message consumer failed!");
    }
}
```

## Q&A
> Q: org.springframework.messaging.MessageDeliveryException: Dispatcher has no subscribers for channel 'application:
> A: 类上需要添加注解`@Service`