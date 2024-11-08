package ru.iteco.accountbank.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.annotation.CacheResult;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheResultBeanPostProcessor implements BeanPostProcessor {
    Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("cache process for bean: " + beanName);
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(CacheResult.class)) {
                System.out.println("Bean needs proxy");
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice((MethodInterceptor) invocation -> {
                    String key = cacheKey(invocation);
                    if (cacheMap.containsKey(key)){
                        System.out.println("Returning of cached result");
                        return cacheMap.get(key);
                    }
                    System.out.println("Before calling of method in bean");
                    Object proceed = invocation.proceed();
                    for (Method declaredMethod : invocation.getThis().getClass().getDeclaredMethods()) {
                        if (invocation.getMethod().getName().equals(declaredMethod.getName()) &&
                                AnnotationUtils.findAnnotation(declaredMethod, CacheResult.class) != null) {
                            System.out.println("Method " + invocation.getMethod().getName() + ", result of which to be cached");
                            cache(key, proceed);
                        }
                    }
                    return proceed;
                });
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }

    private String cacheKey(MethodInvocation invocation) {
        String className = invocation.getClass().getName();
        String methodName = invocation.getMethod().getName();
        String argsNames = Arrays.toString(invocation.getArguments());
        String result = className + "#" + methodName + "#" + "(" + argsNames + ")";
        System.out.println("generated key: " + result);
        return result;
    }

    private void cache(String key, Object proceed) {
        cacheMap.put(key, proceed);
    }
}
