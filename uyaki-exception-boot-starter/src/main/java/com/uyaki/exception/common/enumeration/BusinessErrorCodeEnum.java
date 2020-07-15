package com.uyaki.exception.common.enumeration;

import com.uyaki.exception.common.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务响应枚举
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Getter
@AllArgsConstructor
public enum BusinessErrorCodeEnum implements BusinessExceptionAssert {

    /**
     * 登录已过期
     */
    LAND_EXPIRATION(-5, "登录已过期，为保障账号安全，系统要求您重新登录!");

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;
}
