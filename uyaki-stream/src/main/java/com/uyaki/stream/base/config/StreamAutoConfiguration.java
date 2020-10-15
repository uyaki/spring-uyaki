package com.uyaki.stream.base.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @date 2020/10/15
 */
@EnableBinding(value = {Source.class, Sink.class})
public class StreamAutoConfiguration {
}
