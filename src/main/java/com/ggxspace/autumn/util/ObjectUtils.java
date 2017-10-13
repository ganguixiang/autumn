package com.ggxspace.autumn.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * 对象工具类
 * Created by ganguixiang on 2017/10/13.
 */
public class ObjectUtils {

    /**
     * 要求对象不为空
     * @param o
     */
    public static void requireNonNull(Object o) {
        requireNonNull(o, null);
    }

    /**
     * 要求对象不为空
     * @param o
     * @param message
     */
    public static void requireNonNull(Object o, String message) {
        Objects.requireNonNull(o, message);
        // 对字符串判断
        if (o instanceof String) {
            if (StringUtils.isBlank((String) o)) {
                throw new RuntimeException(message);
            }
        }
        // 对集合判断
        if (o instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) o)) {
                throw new RuntimeException(message);
            }
        }
    }

    /**
     * 判断对象是否为空
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        // 对字符串判断
        if (o instanceof String) {
            if (StringUtils.isBlank((String) o)) {
               return true;
            }
        }
        // 对集合判断
        if (o instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) o)) {
                return true;
            }
        }

        if (Objects.isNull(o)) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否不为空
     * @param o
     * @return
     */
    public static boolean nonNull(Object o) {
        return !isNull(o);
    }
}
