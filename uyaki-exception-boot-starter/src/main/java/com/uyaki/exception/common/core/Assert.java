package com.uyaki.exception.common.core;

import java.util.Collection;
import java.util.Map;

/**
 * <p> 枚举类异常断言，提供简便的方式判断条件，并在条件满足时抛出异常 </p>
 * <p> 错误码和错误信息定义在枚举类中，在本断言方法中，传递错误信息需要的参数 </p>
 *
 * @author uyaki
 * @date 2020 /06/06
 */
public interface Assert {
    /**
     * 创建异常
     *
     * @param args the args
     * @return base exception
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     *
     * @param t    the t
     * @param args the args
     * @return base exception
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * 断言对象为NULL就抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertIsNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * 断言对象为NULL就抛出异常
     *
     * @param obj  待判断布尔变量
     * @param args message 占位符对应的参数列表
     */
    default void assertIsNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    /**
     * 断言对象不为NULL就抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * 断言对象不为NULL就抛出异常
     *
     * @param obj  待判断对象
     * @param args message 占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * 如果字符串为空串，则抛出异常
     *
     * @param str 待判断字符串
     */
    default void assertEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            throw newException();
        }
    }

    /**
     * 如果字符串为空串，则抛出异常
     *
     * @param str  待判断字符串
     * @param args message 占位符对应的参数列表
     */
    default void assertEmpty(String str, Object... args) {
        if (str == null || "".equals(str.trim())) {
            throw newException(args);
        }
    }

    /**
     * 如果数组大小为 0，则抛出异常
     *
     * @param arrays 待判断数组
     */
    default void assertArrayEmpty(Object[] arrays) {
        if (arrays == null || arrays.length == 0) {
            throw newException();
        }
    }

    /**
     * 如果数组大小为 0，则抛出异常
     *
     * @param arrays 待判断数组
     * @param args   message 占位符对应的参数列表
     */
    default void assertArrayEmpty(Object[] arrays, Object... args) {
        if (arrays == null || arrays.length == 0) {
            throw newException(args);
        }
    }

    /**
     * 如果集合大小为 0，则抛出异常
     *
     * @param c 待判断数组
     */
    default void assertCollectionEmpty(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            throw newException();
        }
    }

    /**
     * 如果集合大小为 0，则抛出异常
     *
     * @param c    待判断数组
     * @param args message 占位符对应的参数列表
     */
    default void assertCollectionEmpty(Collection<?> c, Object... args) {
        if (c == null || c.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * 如果 Map大小为 0，则抛出异常
     *
     * @param map 待判断 Map
     */
    default void assertMapEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            throw newException();
        }
    }

    /**
     * 如果 Map大小为 0，则抛出异常
     *
     * @param map  待判断 Map
     * @param args message 占位符对应的参数列表
     */
    default void assertMapEmpty(Map<?, ?> map, Object... args) {
        if (map == null || map.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * 如果布尔值 <code>expression</code> 为 true，则抛出异常
     *
     * @param expression 待判断布尔变量
     */
    default void assertTrue(boolean expression) {
        if (expression) {
            throw newException();
        }
    }

    /**
     * 如果布尔值 <code>expression</code> 为 true，则抛出异常
     *
     * @param expression 待判断布尔变量
     * @param args       message 占位符对应的参数列表
     */
    default void assertTrue(boolean expression, Object... args) {
        if (expression) {
            throw newException(args);
        }
    }

    /**
     * 如果布尔值 <code>expression</code> 为 false，则抛出异常
     *
     * @param expression 待判断布尔变量
     */
    default void assertFalse(boolean expression) {
        if (!expression) {
            throw newException();
        }
    }

    /**
     * 如果布尔值 <code>expression</code> 为 false，则抛出异常
     *
     * @param expression 待判断布尔变量
     * @param args       message 占位符对应的参数列表
     */
    default void assertFalse(boolean expression, Object... args) {
        if (!expression) {
            throw newException(args);
        }
    }

    /**
     * <p> 直接抛出异常
     */
    default void assertFail() {
        throw newException();
    }

    /**
     * <p> 直接抛出异常
     *
     * @param args message 占位符对应的参数列表
     */
    default void assertFail(Object... args) {
        throw newException(args);
    }

    /**
     * <p> 直接抛出异常，并包含原异常信息
     * <p> 当捕获非运行时异常（非继承 {@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的 cause
     *
     * @param t 原始异常
     */
    default void assertFail(Throwable t) {
        throw newException(t);
    }

    /**
     * <p> 直接抛出异常，并包含原异常信息
     * <p> 当捕获非运行时异常（非继承 {@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的 cause
     *
     * @param t    原始异常
     * @param args message 占位符对应的参数列表
     */
    default void assertFail(Throwable t, Object... args) {
        throw newException(t, args);
    }

    /**
     * 如果两对象不相等，则抛出异常
     *
     * @param o1 待判断对象，若 <code>o1</code> 为 null，也当作不相等处理
     * @param o2 待判断对象
     */
    default void assertNotEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException();
        }
        if (!o1.equals(o2)) {
            throw newException();
        }
    }

    /**
     * 如果两对象不相等，则抛出异常
     *
     * @param o1   待判断对象，若 <code>o1</code> 为 null，也当作不相等处理
     * @param o2   待判断对象
     * @param args message 占位符对应的参数列表
     */
    default void assertNotEquals(Object o1, Object o2, Object... args) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException(args);
        }
        if (!o1.equals(o2)) {
            throw newException(args);
        }
    }
}
