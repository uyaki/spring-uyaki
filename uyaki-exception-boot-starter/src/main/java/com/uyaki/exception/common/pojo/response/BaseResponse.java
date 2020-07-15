package com.uyaki.exception.common.pojo.response;

import com.uyaki.exception.common.core.IResponseEnum;
import com.uyaki.exception.common.enumeration.CommonErrorCodeEnum;
import lombok.Data;

/**
 * The type Base response.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    /**
     * Instantiates a new Base response.
     */
    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonErrorCodeEnum.SUCCESS);
    }

    /**
     * Instantiates a new Base response.
     *
     * @param responseEnum the response enum
     */
    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    /**
     * Instantiates a new Base response.
     *
     * @param code    the code
     * @param message the message
     */
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
