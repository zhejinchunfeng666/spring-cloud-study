package com.zf.study.core.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 * @author zf
 */
@Target(ElementType.METHOD) // 该注解用在什么地方
@Retention(RetentionPolicy.RUNTIME) // 注解传递存活时间 注解可保留到程序运行时并被加载到 JVM 中，因此可通过反射机制读取注解的信息
@Documented // 将注解包含到 Javadoc 中
public @interface RateLimiter2 {

    long DEFAULT_REQUEST = 10;

    // 令牌桶最大容量
    long bucketCapacity() default DEFAULT_REQUEST;

    // 限流唯一标识
    String key() default "";

    // 每次添加令牌数
    long addToken() default 1;

    // 令牌添加间隔
    long addInterval() default 1;

    // 时间单位 默认秒
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
