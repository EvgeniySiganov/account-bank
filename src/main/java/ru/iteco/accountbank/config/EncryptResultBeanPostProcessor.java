package ru.iteco.accountbank.config;

import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import ru.iteco.accountbank.model.annotation.EncryptResult;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.lang.reflect.Method;
import java.util.Base64;

@Component
public class EncryptResultBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Encrypt process for bean: " + beanName);
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(EncryptResult.class)) {
                System.out.println("Bean needs proxy");
                ProxyFactory proxyFactory = new ProxyFactory(bean);
                proxyFactory.addAdvice((MethodInterceptor) invocation -> {
                    System.out.println("Before calling of method in bean");
                    Object proceed = invocation.proceed();
                    for (Method declaredMethod : invocation.getThis().getClass().getDeclaredMethods()) {
                        if (invocation.getMethod().getName().equals(declaredMethod.getName()) &&
                                AnnotationUtils.findAnnotation(declaredMethod, EncryptResult.class) != null) {
                            System.out.println("Call " + invocation.getMethod().getName() + ", result of which to be encrypted");
                            return encrypt(proceed);
                        }
                    }

//                    if (invocation.getMethod().isAnnotationPresent(EncryptResult.class)) {
//                        System.out.println("Call " + invocation.getMethod().getName() + ", result of which to be encrypted");
//                        return encrypt(proceed);
//                    }
                    return proceed;
                });
                return proxyFactory.getProxy();
            }
        }
        return bean;
    }

    @SneakyThrows
    private Object encrypt(Object proceed) {
        Cipher cipher = Cipher.getInstance("AES");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey());
        byte[] bytes = cipher.doFinal(SerializationUtils.serialize(proceed));
        String result = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Encrypt result is: " + result);
        return result;
    }
}
