package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("allGetMethod() || allSetMethod()")
    public void beforeAllGetMethodAdvice(JoinPoint joinPoint) {
        log.info("beforeAllGetOrSetMethodAdvice:: Call method: {} with arguments {}", joinPoint.toShortString(), joinPoint.getArgs());
    }

    @Around("allMethodServicePackage()")
    public Object aroundAllMethodServicePackage(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("aroundAllMethodServicePackage:: {}", joinPoint.toShortString());
        return joinPoint.proceed();
    }

    @AfterThrowing(value = "allMethods()", throwing = "e")
    public void afterThrowingAllMethodAdvice(JoinPoint.StaticPart staticPart, Exception e) {
        log.info("afterThrowingAllMethodAdvice:: {} {}", staticPart.toShortString(), e);
    }

    @Pointcut("execution(public * get*(..))")
    public void allGetMethod() {}

    @Pointcut("execution(public * set*(..))")
    public void allSetMethod() {}

    @Pointcut("within(ru.iteco.accountbank.service.*)")
    public void allMethodServicePackage(){}

    @Pointcut("execution(* *(..))")
    public void allMethods(){}
}
