package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AfterAspect {

    @AfterReturning(value = "allGetMethod()", returning = "result")
    public void afterAllGetMethod(JoinPoint.StaticPart staticPart, Object result) {
        log.info("afterAllGetMethod:: After returning {} with result {}", staticPart.toShortString(), result);
    }

    @AfterThrowing(value = "allSaveMethod()", throwing = "exception")
    public void afterSaveMethodThrowAdvice(JoinPoint.StaticPart staticPart, Exception exception) {
        log.info("afterSaveMethodThrowAdvice:: After throwing {} with exception {}", staticPart.toShortString(), exception.getMessage());
    }

    @After("allSaveMethod() || allGetMethod()")
    public void afterAllSaveOrGetMethodAdvice(JoinPoint joinPoint) {
        log.info("afterAllSaveOrGetMethodAdvice:: After {} with args {}", joinPoint.toShortString(), joinPoint.getArgs());
    }

    @Pointcut("execution(* get*(..))")
    public void allGetMethod() {}

    @Pointcut("execution(* save*(..))")
    public void allSaveMethod() {}
}
