package com.gongyu.mydemo.enums;

import com.gongyu.mydemo.bean.result.BaseStatus;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class Asserts {
    public Asserts() {
    }

    public static void isTrue(boolean expression, BaseStatus error, Object... args) {
        if (!expression) {
            throw new BaseException(error, args);
        }
    }

    public static void isNull(Object obj, BaseStatus error, Object... args) {
        if (Objects.nonNull(obj)) {
            throw new BaseException(error, args);
        }
    }

    public static void notNull(Object obj, BaseStatus error, Object... args) {
        if (Objects.isNull(obj)) {
            throw new BaseException(error, args);
        }
    }

    public static void isEmpty(Object obj, BaseStatus error, Object... args) {
        if (!StringUtils.isEmpty(obj)) {
            throw new BaseException(error, args);
        }
    }

    public static void notEmpty(Object obj, BaseStatus error, Object... args) {
        if (StringUtils.isEmpty(obj)) {
            throw new BaseException(error, args);
        }
    }

    public static void equals(Object obj1, Object obj2, BaseStatus error, Object... args) {
        if (Objects.nonNull(obj1)) {
            if (!obj1.equals(obj2)) {
                throw new BaseException(error, args);
            }
        } else if (obj1 != obj2) {
            throw new BaseException(error, args);
        }

    }

    public static void notEquals(Object obj1, Object obj2, BaseStatus error, Object... args) {
        if (Objects.nonNull(obj1)) {
            if (obj1.equals(obj2)) {
                throw new BaseException(error, args);
            }
        } else if (obj1 == obj2) {
            throw new BaseException(error, args);
        }

    }
}
