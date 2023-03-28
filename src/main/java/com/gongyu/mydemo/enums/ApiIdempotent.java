package com.gongyu.mydemo.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 在需要保证 接口幂等性 的Controller的方法上使用此注解
 * @author wengwx
 * @date 2023/3/22
 * @des  自定义拦截注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
}
