package com.uyaki.exception.common;

import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.enumeration.ArgumentErrorCodeEnum;
import com.uyaki.exception.common.enumeration.CommonErrorCodeEnum;
import com.uyaki.exception.common.enumeration.ServletErrorCodeEnum;
import com.uyaki.exception.common.exception.ArgumentException;
import com.uyaki.exception.common.exception.BusinessException;
import com.uyaki.exception.common.i18n.I18nComponent;
import com.uyaki.exception.common.pojo.response.ErrorResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;


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
@ConditionalOnBean(name = "apiExceptionHandler")
public class UnifiedApiExceptionHandler {
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
     * @param e the e
     * @return the error response
     */
    @ExceptionHandler(value = ArgumentException.class)
    @ResponseBody
    public ErrorResponse handleArgumentException(ArgumentException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ErrorResponse handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * 其他自定义异常
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ErrorResponse handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);

        return new ErrorResponse(e.getResponseEnum().getCode(), getMessage(e));
    }

    /**
     * Controller 上一层相关异常
     *
     * @param e 异常
     * @return 异常结果 error response
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
    @ResponseBody
    public ErrorResponse handleServletException(Exception e) {
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
            code = CommonErrorCodeEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonErrorCodeEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(code, e.getMessage());
    }


    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ErrorResponse handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验 (Valid) 异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidException(MethodArgumentNotValidException e) {
        log.error(" 参数绑定校验异常 ", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private ErrorResponse wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();

        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());

        }
        Integer code = ArgumentErrorCodeEnum.VALID_ERROR.getCode();
        String message = i18nComponent.getLocaleMessage("", msg.substring(2), null);
        return new ErrorResponse(code, message);
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果 error response
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境，不适合把具体的异常信息展示给用户，比如数据库异常信息.
            int code = CommonErrorCodeEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonErrorCodeEnum.SERVER_ERROR);
            String message = getMessage(baseException);
            return new ErrorResponse(code, message);
        }

        return new ErrorResponse(CommonErrorCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
    }
}
