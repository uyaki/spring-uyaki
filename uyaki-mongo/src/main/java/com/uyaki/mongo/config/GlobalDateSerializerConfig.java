package com.uyaki.mongo.config;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The type Global date serializer config.
 *
 * @date 2020 /07/17
 */
@Configuration
public class GlobalDateSerializerConfig {
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    /**
     * Local date time deserializer local date time serializer.
     *
     * @return the local date time serializer
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Date serializer date serializer.
     * 如不需要使用Date，可去除
     *
     * @return the date serializer
     */
    @Bean
    public DateSerializer dateSerializer() {
        return new DateSerializer(false, new SimpleDateFormat(pattern));
    }

    /**
     * Jackson 2 object mapper builder customizer jackson 2 object mapper builder customizer.
     *
     * @return the jackson 2 object mapper builder customizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
            // 如不需要使用Date，可去除
            builder.serializerByType(Date.class, dateSerializer());
        };
    }
}
