package com.uyaki.web.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用RestControllerAdvice的beforeBodyWrite方法时，在处理String时，实际处理的HttpMessageConverter，应该是MappingJackson2HttpMessageConverter
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 需要先定义一个 convert 转换消息的对象;
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // fastJson配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /*
           分别设置：将Collection类型字段的字段空值输出为[]
                          将字符串类型字段的空值输出为空字符串 ""
                          将数值类型字段的空值输出为0
                          将Boolean类型字段的空值输出为false
                          当有枚举类型的时候输出该枚举的toString方法
                          格式化Json数据
         */
        SerializerFeature[] serializerFeatureArray = new SerializerFeature[]{
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatureArray);
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 将convert添加到converters当中
        converters.add(7, fastConverter);
    }
}
