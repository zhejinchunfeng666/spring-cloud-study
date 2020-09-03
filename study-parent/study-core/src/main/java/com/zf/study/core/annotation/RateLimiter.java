package com.zf.study.core.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 * @author zf
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    long DEFAULT_REQUEST = 10;

    @AliasFor("max")
    long max() default DEFAULT_REQUEST;

    @AliasFor("value")
    long value() default DEFAULT_REQUEST;

    String key() default "";
    
    long timeout() default 1;

    TimeUnit timeunit() default TimeUnit.SECONDS;
}
