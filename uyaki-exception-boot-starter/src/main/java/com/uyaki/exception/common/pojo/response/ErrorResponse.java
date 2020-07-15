package com.uyaki.exception.common.pojo.response;

/**
 * The type Error response.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public class ErrorResponse extends BaseResponse {

    /**
     * Instantiates a new Error response.
     */
    public ErrorResponse() {
    }

    /**
     * Instantiates a new Error response.
     *
     * @param code    the code
     * @param message the message
     */
    public ErrorResponse(int code, String message) {
        super(code, message);
    }
}
