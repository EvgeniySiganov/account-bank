package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Slf4j
@Component
public class AnnotationAspect {
    @Before("@annotation(ru.iteco.accountbank.model.annotation.CacheResult)")
    public void beforeAllAnnotationCacheResultAdvice() {
        log.info("beforeAllAnnotationCacheResultAdvice:: before call method annotated @CacheResult");
    }
}
