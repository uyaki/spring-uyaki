package com.uyaki.exception.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * The enum Servlet response enum.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Getter
@AllArgsConstructor
public enum ServletErrorCodeEnum {
    /**
     * Method argument not valid exception servlet response enum.
     */
    MethodArgumentNotValidException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Method argument type mismatch exception servlet response enum.
     */
    MethodArgumentTypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Missing servlet request part exception servlet response enum.
     */
    MissingServletRequestPartException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Missing path variable exception servlet response enum.
     */
    MissingPathVariableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Bind exception servlet response enum.
     */
    BindException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Missing servlet request parameter exception servlet response enum.
     */
    MissingServletRequestParameterException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Type mismatch exception servlet response enum.
     */
    TypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Servlet request binding exception servlet response enum.
     */
    ServletRequestBindingException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * Http message not readable exception servlet response enum.
     */
    HttpMessageNotReadableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    /**
     * No handler found exception servlet response enum.
     */
    NoHandlerFoundException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    /**
     * No such request handling method exception servlet response enum.
     */
    NoSuchRequestHandlingMethodException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    /**
     * Http request method not supported exception servlet response enum.
     */
    HttpRequestMethodNotSupportedException(4405, "", HttpServletResponse.SC_METHOD_NOT_ALLOWED),
    /**
     * Http media type not acceptable exception servlet response enum.
     */
    HttpMediaTypeNotAcceptableException(4406, "", HttpServletResponse.SC_NOT_ACCEPTABLE),
    /**
     * Http media type not supported exception servlet response enum.
     */
    HttpMediaTypeNotSupportedException(4415, "", HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE),
    /**
     * Conversion not supported exception servlet response enum.
     */
    ConversionNotSupportedException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    /**
     * Http message not writable exception servlet response enum.
     */
    HttpMessageNotWritableException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    /**
     * Async request timeout exception servlet response enum.
     */
    AsyncRequestTimeoutException(4503, "", HttpServletResponse.SC_SERVICE_UNAVAILABLE);

    /**
     * 返回码，目前与 {@link #statusCode} 相同
     */
    private int code;
    /**
     * 返回信息，直接读取异常的 message
     */
    private String message;
    /**
     * HTTP 状态码
     */
    private int statusCode;
}
