package com.uyaki.exception.common.exception;

import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.core.IResponseEnum;

/**
 * The type Validation exception.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public class ValidationException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Validation exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     */
    public ValidationException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    /**
     * Instantiates a new Validation exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     * @param cause        the cause
     */
    public ValidationException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
