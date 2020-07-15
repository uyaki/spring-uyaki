package com.uyaki.exception.common.exception;

import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.core.IResponseEnum;

/**
 * The type Business exception.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public class BusinessException extends BaseException {
    /**
     * Instantiates a new Business exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     */
    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     * @param cause        the cause
     */
    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
