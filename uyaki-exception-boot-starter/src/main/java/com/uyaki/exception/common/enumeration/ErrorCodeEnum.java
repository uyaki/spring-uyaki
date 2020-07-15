package com.uyaki.exception.common.enumeration;

import com.uyaki.exception.common.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Response enum.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements BusinessExceptionAssert {
    /**
     * Bad licence type
     */
    BAD_LICENCE_TYPE(7001, "Bad licence type."),
    /**
     * Licence not found
     */
    LICENCE_NOT_FOUND(7002, "Licence not found.");

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
}
