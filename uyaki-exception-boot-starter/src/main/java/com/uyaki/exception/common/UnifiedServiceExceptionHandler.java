package com.uyaki.exception.common;

import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.enumeration.ArgumentErrorCodeEnum;
import com.uyaki.exception.common.enumeration.CommonErrorCodeEnum;
import com.uyaki.exception.common.enumeration.ServletErrorCodeEnum;
import com.uyaki.exception.common.exception.ArgumentException;
import com.uyaki.exception.common.exception.BusinessException;
import com.uyaki.exception.common.i18n.I18nComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 统一异常处理器
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Slf4j
@Component
@RestControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnBean(name = "serviceExceptionHandler")
public class UnifiedServiceExceptionHandler {
    /**
     * The constant MESSAGE_FORMAT.
     */
    public static final String MESSAGE_FORMAT = "%s|%s|%s|%s";
    /**
     * The constant VALID_ERROR_CODE.
     */
    public static final Integer VALID_ERROR_CODE = -20;
    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    @Resource
    private I18nComponent i18nComponent;

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active:default}")
    private String profile;

    /**
     * 获取国际化消息
     *
     * @param e 异常
     * @return message message
     */
    public String getMessage(BaseException e) {
        String code = e.getResponseEnum().toString();
        String message = i18nComponent.getLocaleMessage(e.getResponseEnum().getMessage(), code, e.getArgs());
        // 如果还是没有定义，读取Runtime message
        if (StringUtils.isEmpty(message)) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 参数异常
     *
     * @param e        the e
     * @param response the response
     */
    @ExceptionHandler(value = ArgumentException.class)
    public void handleArgumentException(ArgumentException e, HttpServletResponse response) {
        try {
            log.error(e.getMessage(), e);
            String i18nMessage = getMessage(e);
            String message = String.format(MESSAGE_FORMAT, "VALIDATION", VALID_ERROR_CODE, i18nMessage, "");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 业务异常
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler(value = BusinessException.class)
    public void handleBusinessException(BaseException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        try {
            log.error(e.getMessage(), e);
            String message = String.format(MESSAGE_FORMAT, "BUSINESS", e.getResponseEnum().getCode(), getMessage(e), "");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 其他自定义异常
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler(value = BaseException.class)
    public void handleBaseException(BaseException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        log.error(e.getMessage(), e);
        try {
            log.error(e.getMessage(), e);
            String message = String.format(MESSAGE_FORMAT, "Base", e.getResponseEnum().getCode(), getMessage(e), "");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Controller 上一层相关异常
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public void handleServletException(Exception e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        int code = CommonErrorCodeEnum.SERVER_ERROR.getCode();
        try {
            ServletErrorCodeEnum servletExceptionEnum = ServletErrorCodeEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletErrorCodeEnum.class.getName());
        }

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境，不适合把具体的异常信息展示给用户，比如 404.
            BaseException baseException = new BaseException(CommonErrorCodeEnum.SERVER_ERROR);
            String message = String.format(MESSAGE_FORMAT, "SERVLET", CommonErrorCodeEnum.SERVER_ERROR.getCode(), getMessage(baseException), "");
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        try {
            String message = String.format(MESSAGE_FORMAT, "SERVLET", code, e.getMessage(), "");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    /**
     * 参数绑定异常
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler(value = BindException.class)
    public void handleBindException(BindException e, HttpServletResponse response) {
        log.error("参数绑定校验异常", e);
        wrapperBindingResult(e.getBindingResult(), response);
    }

    /**
     * 参数校验 (Valid) 异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void handleValidException(MethodArgumentNotValidException e, HttpServletResponse response) {
        log.error(" 参数绑定校验异常 ", e);
        wrapperBindingResult(e.getBindingResult(), response);
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     */
    private void wrapperBindingResult(BindingResult bindingResult, HttpServletResponse response) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());

        }
        Integer code = ArgumentErrorCodeEnum.VALID_ERROR.getCode();
        String msgs = i18nComponent.getLocaleMessage("", msg.substring(2), null);
        String message = String.format(MESSAGE_FORMAT, "BIND", code, msgs, "");
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 未定义异常
     *
     * @param e        异常
     * @param response the response
     */
    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e, HttpServletResponse response) {
        log.error(e.getMessage(), e);

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境，不适合把具体的异常信息展示给用户，比如数据库异常信息.
            BaseException baseException = new BaseException(CommonErrorCodeEnum.SERVER_ERROR);
            String message = String.format(MESSAGE_FORMAT, "UNKNOWN", CommonErrorCodeEnum.SERVER_ERROR.getCode(), getMessage(baseException), "");
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        String message = String.format(MESSAGE_FORMAT, "UNKNOWN", CommonErrorCodeEnum.SERVER_ERROR.getCode(), e.getMessage(), "");
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
