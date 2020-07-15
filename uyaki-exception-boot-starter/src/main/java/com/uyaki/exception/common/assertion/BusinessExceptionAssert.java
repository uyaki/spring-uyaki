package com.uyaki.exception.common.assertion;

import com.uyaki.exception.common.core.Assert;
import com.uyaki.exception.common.core.IResponseEnum;
import com.uyaki.exception.common.core.BaseException;
import com.uyaki.exception.common.exception.BusinessException;

import java.text.MessageFormat;

/**
 * 业务异常断言
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }
}
