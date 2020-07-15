# uyaki-exception

## Usage

### 引入依赖

```xml
<parent>
    <groupId>com.uyaki</groupId>
    <artifactId>spring-uyaki</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

```groovy
dependencies {
    api(project(":uyaki-exception-boot-starter"))
}
```

### 使用示例

|Component文件|描述|加载bean|
|:-----:|:-----:|:-----:|
|UnifiedApiExceptionHandler.class|拦截异常后以Response的返回形式返回|@ConditionalOnBean(name = "apiExceptionHandler")|
|UnifiedServiceExceptionHandler.class|拦截异常后以500的异常抛出|@ConditionalOnBean(name = "serviceExceptionHandler")|

配置@Bean，根据name加载对应的组件

```java
/**
 *  根据Bean的name加载对应的配置文件
 */
@Bean
public void apiExceptionHandler(){
}
```

```java
@GetMapping("/argumnet")
    public String argumnet() {
        String jj = null;
        ArgumentErrorCodeEnum.VALID_ERROR.assertEquals(jj, "as","jj:为空");
        return "hello";
    }
    @GetMapping("/business")
    public String business() {
        String jj = null;
        BusinessErrorCodeEnum.LAND_EXPIRATION.assertEquals(jj, "as");
        return "hello";
    }
```

## 实现原理

### 用断言代替throw Exception

> 以BusinessErrorCodeEnum为例子

#### Assert声明核心方法

![](https://cdn.jsdelivr.net/gh/uyaki/pic-cloud/img/20200618143237.png)

```java
public interface Assert {
    /**
     * 创建异常
     *
     * @param args the args
     * @return base exception
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     *
     * @param t    the t
     * @param args the args
     * @return base exception
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * <p> 断言对象 <code>obj</code> 为 <code>null</code>。如果对象 <code>obj</code> 不为 <code>null</code>，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertIsNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }
    // 其他default方法
    ...
}
```

> 所有继承Assert的接口都要实现默认实现`newException`方法，实现异常抛出，当断言失败的时候 `throw newException`

```java
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }
}
```

#### BusinessErrorCodeEnum实现断言抛出异常

![](https://cdn.jsdelivr.net/gh/uyaki/pic-cloud/img/20200618143312.png)

即可使用BusinessErrorCodeEnum.XXXX.assertIsNull(xxx)实现异常抛出

### 异常拦截

核心类：UnifiedExceptionHandler

```java
@Slf4j
@Component
@ControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnMissingBean(UnifiedExceptionHandler.class)
public class UnifiedExceptionHandler {
    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ErrorResponse handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }
}
```

### i18n异常消息国际化

#### 配置消息国际化语言包位置

```java
@Configuration
public class ResourceBundleConfig {
    /**
     * Message source resource bundle message source.
     *
     * @return the resource bundle message source
     */
    @Bean
    @Primary
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        // 指定国际化资源目录,其中i18n/validation为文件夹，ValidationErrorMsg为国际化文件前缀
        bundleMessageSource.setBasenames("i18n/validation/ErrorMsg");
        bundleMessageSource.setCacheMillis(10);
        return bundleMessageSource;
    }
}

```

#### i18n组件
```java
@Slf4j
@Component
public class I18nComponent {
    /**
     * 默认语言包: 简体中文、英文
     */
    private static final List<Locale> DEFAULT_ACCEPT_LOCALES = Arrays.asList(Locale.SIMPLIFIED_CHINESE, Locale.US);

    /**
     * 请求头信息，通过此解析locale
     */
    public static final String HTTP_ACCEPT_LANGUAGE = "Accept-Language";

    @Resource
    private ResourceBundleMessageSource bundleMessageSource;

    /**
     * 获取 locale.
     *
     * @return the locale
     */
    public Locale getLocale() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = Objects.requireNonNull(servletRequestAttributes).getRequest();
        String locale = httpServletRequest.getHeader(HTTP_ACCEPT_LANGUAGE);
        if (StringUtils.isEmpty(locale)) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(locale);
        return Locale.lookup(languageRanges, DEFAULT_ACCEPT_LOCALES);
    }


    /**
     * 获取国际化消息
     *
     * @param defaultMessage the default message
     * @param propertyKey    the property key
     * @param args           the args
     * @return the locale message
     */
    public String getLocaleMessage(String defaultMessage, String propertyKey, Object[] args) {
        Locale locale = getLocale();
        try {
            return bundleMessageSource.getMessage(propertyKey, args, locale);
        } catch (NoSuchMessageException e) {
            // 如果语言包不存在
            log.error("请检查i18n语言包 -> {}", e.getMessage());
        }
        return defaultMessage;
    }
}
```
#### 在UnifiedExceptionHandler异常拦截里统一处理
```java
@Resource
private I18nComponent i18nComponent;

/**
    * 当前环境
    */
@Value("${spring.profiles.active:default}")
private String profile;

/**
    * 获取国际化消息
    *
    * @param e 异常
    * @return message message
    */
public String getMessage(BaseException e) {
    String code = e.getResponseEnum().toString();
    String message = i18nComponent.getLocaleMessage(e.getResponseEnum().getMessage(), code, e.getArgs());
    // 如果还是没有定义，读取Runtime message
    if (StringUtils.isEmpty(message)) {
        return e.getMessage();
    }
    return message;
}
```
