package com.uyaki.exception.common.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 加载Resource Bundle配置，读取国际化资源目录
 *
 * @author uyaki
 * @date 2020 /06/06
 */
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
