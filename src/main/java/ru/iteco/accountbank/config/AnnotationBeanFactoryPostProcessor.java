package ru.iteco.accountbank.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.annotation.CacheResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @SneakyThrows
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        for (String beanDefinitionName : configurableListableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.isPrototype()) {
                List<String> methods = new ArrayList<>();
                for (Method declaredMethod : Class.forName(beanDefinition.getBeanClassName()).getDeclaredMethods()) {
                    if (declaredMethod.isAnnotationPresent(CacheResult.class)) {
                        methods.add(declaredMethod.getName());
                    }
                }
                if (!methods.isEmpty()) {
                    System.out.println("bean " + beanDefinition.getBeanClassName() + " has prototype scope and cached "
                    + "methods " + methods);
                }
            }
        }
    }
}
