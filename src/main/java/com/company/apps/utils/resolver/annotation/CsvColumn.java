package com.company.apps.utils.resolver.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Component
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvColumn {

    String defaultValue() default "";

    String nameForParsing();
}