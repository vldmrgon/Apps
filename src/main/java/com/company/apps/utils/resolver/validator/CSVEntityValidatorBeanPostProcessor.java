package com.company.apps.utils.resolver.validator;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeansException;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CSVEntityValidatorBeanPostProcessor implements BeanPostProcessor {

    private ConcurrentHashMap<String, ?> cache = new ConcurrentHashMap<>();

    /*
     *    1) Search all objects with annotation CsvEntity
     *    2)
     *    3) Check fields in an object (all fields must annotated @CsvColumn or @CsvNestedEntity)
     *
     *
     *
     *
     *
     *
     *
     * */

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}