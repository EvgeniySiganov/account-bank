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

    @Around("checkRequestAnnotationAndExternalInfoInArgsMethod(externalInfo)")
    public Object beforeAnnotationCheckRequestAndExternalInfoInArgsMethods(ProceedingJoinPoint proceedingJoinPoint, ExternalInfo externalInfo) throws Throwable {
        if (!externalInfo.getId().equals(idNotProcess)) {
            log.info("beforeAnnotationCheckRequestAndExternalInfoInArgsMethods:: id {} is accepted", externalInfo.getId());
            return proceedingJoinPoint.proceed();
        } else {
            throw new IllegalAccessException("Id: " + externalInfo.getId() + " is not accepted");
        }
    }

    @Pointcut("@annotation(ru.iteco.accountbank.model.annotation.CheckRequest) && args(externalInfo, ..)")
    public void checkRequestAnnotationAndExternalInfoInArgsMethod(ExternalInfo externalInfo){}

}
