package ru.iteco.accountbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class CacheResultAspect {
    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    @Around("cacheResultAnnotationMethods()")
    public Object cacheResultAnnotationMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Caching process for method: {}" + proceedingJoinPoint.toShortString());
        String key = cacheKey(proceedingJoinPoint);
        if (cacheMap.containsKey(key)) {
            log.info("Method is in cache, returning the result {}", cacheMap.get(key));
            return cacheMap.get(key);
        }
        Object result = proceedingJoinPoint.proceed();
        log.info("The result: {} needs caching", result);
        cacheMap.put(key, result);
        return result;
    }

    private String cacheKey(ProceedingJoinPoint invocation) {
        String className = invocation.getThis().getClass().getSimpleName();
        String methodName = invocation.getSignature().getName();
        String argsNames = Arrays.toString(invocation.getArgs());
        String result = className + "#" + methodName + "#" + "(" + argsNames + ")";
        System.out.println("generated key: " + result);
        return result;
    }

    @Pointcut("@annotation(ru.iteco.accountbank.model.annotation.CacheResult)")
    void cacheResultAnnotationMethods(){}
}
