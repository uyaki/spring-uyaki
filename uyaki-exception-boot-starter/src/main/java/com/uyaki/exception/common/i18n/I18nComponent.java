package com.uyaki.exception.common.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The type 18 n component.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
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


    /**
     * Gets locale message with place holder.
     *
     * @param defaultMessage the default message
     * @param propertyKey    the property key
     * @param params         the params
     * @return the locale message with place holder
     */
    public String getLocaleMessageWithPlaceHolder(String defaultMessage, String propertyKey, Object... params) {
        Locale locale = getLocale();
        try {
            return bundleMessageSource.getMessage(propertyKey, params, locale);
        } catch (NoSuchMessageException e) {
            // 如果语言包不存在
            log.error("请检查i18n语言包 -> {}", e.getMessage());
        }
        return defaultMessage;
    }

}
