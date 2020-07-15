package com.uyaki.exception.common.core;


import lombok.Getter;

/**
 * The type Base exception.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 返回码
     */
    protected IResponseEnum responseEnum;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    /**
     * Instantiates a new Base exception.
     *
     * @param responseEnum the response enum
     */
    public BaseException(IResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param code the code
     * @param msg  the msg
     */
    public BaseException(int code, String msg) {
        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }
        };
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        super(message);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param responseEnum the response enum
     * @param args         the args
     * @param message      the message
     * @param cause        the cause
     */
    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }

}
