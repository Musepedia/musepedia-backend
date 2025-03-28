package com.mimiter.mgs.common.annotation;

import java.lang.annotation.*;

/**
 * 用于controller的方法上，表示一个接口不需要登录即可访问。
 *
 * @author 王晨晖
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {
}
