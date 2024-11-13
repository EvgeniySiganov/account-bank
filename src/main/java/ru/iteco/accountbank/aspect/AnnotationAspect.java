package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;

@Order(2)
@Aspect
@Slf4j
@Component
public class AnnotationAspect {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @Before("@annotation(ru.iteco.accountbank.model.annotation.CacheResult)")
    public void beforeAllAnnotationCacheResultAdvice() {
        log.info("beforeAllAnnotationCacheResultAdvice:: before call method annotated @CacheResult");
    }

    @Around("checkRequestAnnotationMethod() && methodWithExternalInfo(externalInfo)")
    public void beforeAnnotationCheckRequest(ProceedingJoinPoint proceedingJoinPoint, ExternalInfo externalInfo) throws Throwable {
        if (!externalInfo.getId().equals(idNotProcess)) {
            proceedingJoinPoint.proceed();
        } else {
            throw new IllegalAccessException("Id: " + externalInfo.getId() + " is not accepted");
        }
    }

    @Pointcut("@annotation(ru.iteco.accountbank.model.annotation.CheckRequest)")
    public void checkRequestAnnotationMethod(){}

    @Pointcut("execution(* *(.., ru.iteco.accountbank.model.ExternalInfo, .. )) && args(externalInfo)")
    public void methodWithExternalInfo(ExternalInfo externalInfo){}

}
