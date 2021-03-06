package com.czjy.chaozhi.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by huyg on 2018/9/28.
 */
@Scope
@Retention(RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
