package com.uyaki.exception.common.assertion;

import com.uyaki.exception.common.core.IResponseEnum;
import com.uyaki.exception.common.core.Assert;
import com.uyaki.exception.common.exception.ArgumentException;
import com.uyaki.exception.common.core.BaseException;

import java.text.MessageFormat;

/**
 * The interface Argument exception assert.
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public interface ArgumentExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new ArgumentException(this, args, msg, t);
    }

}