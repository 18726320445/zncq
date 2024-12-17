package com.igeek.zncq.log;

import java.lang.annotation.*;

/**
 * @author liuyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogSys {
    String value() default "";
}
