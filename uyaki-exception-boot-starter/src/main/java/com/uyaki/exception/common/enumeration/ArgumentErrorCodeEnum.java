package com.uyaki.exception.common.enumeration;

import com.uyaki.exception.common.assertion.ArgumentExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数响应枚举
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Getter
@AllArgsConstructor
public enum ArgumentErrorCodeEnum implements ArgumentExceptionAssert {
    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(6000, "{0}"),

    ;
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
}
