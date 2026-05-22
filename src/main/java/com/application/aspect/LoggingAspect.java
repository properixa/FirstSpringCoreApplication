package com.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.application.service.*.*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* com.application.repository.*.*(..))")
    public void repositoryMethods() {}

    @Before("serviceMethods()")
    public void logServiceBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().toShortString() + " before aspect");
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "ex")
    public void logServiceAfterException(JoinPoint joinPoint, Exception ex) {
        System.out.println(joinPoint.getSignature().toShortString() + " throws " + ex.getMessage());
    }

    @AfterReturning(value = "repositoryMethods()", returning = "value")
    public void logRepositoryAfterReturning(JoinPoint joinPoint, Object value) {
        System.out.println(joinPoint.getSignature().toShortString() + " return " + value);
    }
}
