package com.application.processor;

import com.application.annotation.LogExecution;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(LogExecution.class)) {
            System.out.println("Инициализация бина была завершена: " + beanName);
        }
        return bean;
    }

    @Override
    public @Nullable Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(LogExecution.class)) {
            System.out.println("Инициализация бина: " + beanName + " начата");
        }
        return bean;
    }
}
