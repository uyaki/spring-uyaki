package com.uyaki.hello.controller;

import com.uyaki.exception.common.enumeration.ArgumentErrorCodeEnum;
import com.uyaki.exception.common.enumeration.BusinessErrorCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Exception controller.
 *
 * @date 2020 /07/15
 */
@RestController
public class ExceptionController {
    /**
     * Argumnet string.
     *
     * @return the string
     */
    @GetMapping("/argument")
    public String argument() {
        String jj = null;
        ArgumentErrorCodeEnum.VALID_ERROR.assertIsNull(jj, "as", "jj:为空");
        return "hello";
    }

    /**
     * Say hello string.
     *
     * @return the string
     */
    @GetMapping("/business")
    public String business() {
        String jj = null;
        BusinessErrorCodeEnum.LAND_EXPIRATION.assertIsNull(jj, "as");
        return "hello";
    }
}
