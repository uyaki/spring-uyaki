package com.uyaki.exception.common.exception;

import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.core.IResponseEnum;

/**
 * <p> 参数异常 </p>
 * <p> 在处理业务过程中校验参数出现错误，可以抛出该异常 </p>
 * <p> 编写公共代码（如工具类）时，对传入参数检查不通过时，可以抛出该异常 </p>
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public class ArgumentException extends BaseException {
    /**
     * Instantiates a new Argument exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     */
    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    /**
     * Instantiates a new Argument exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     * @param cause        the cause
     */
    public ArgumentException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
