package ru.iteco.accountbank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import ru.iteco.accountbank.model.AccountInfo;
import ru.iteco.accountbank.model.User;
import ru.iteco.accountbank.repository.ExternalRepository;
import ru.iteco.accountbank.service.*;
import ru.iteco.accountbank.service.Process;

@ComponentScan
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@Slf4j
public class AccountBankApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AccountBankApplication.class);
        //springIocDi(applicationContext);
        //homeworkOne(applicationContext);
        aopHomework(applicationContext);


    }

    static void aopHomework(ApplicationContext applicationContext) {
        Flow flow = applicationContext.getBean(Flow.class);
        flow.run(4);
    }

    private static void aop(ApplicationContext applicationContext) {
        User name = User.builder().id(1).name("name").build();
        ExternalRepository externalRepository = applicationContext.getBean(ExternalRepository.class);
        log.info("Return value from externalRepository.getInfo: {}", externalRepository.getInfo(name));
        externalRepository.saveInfo(null);
    }

    private static void homeworkOne(ApplicationContext applicationContext) {
        Flow flow = applicationContext.getBean(Flow.class);
        flow.run(1);
        flow.run(2);
        flow.run(2);
        flow.run(3);
        flow.run(4);
        flow.run(4);
    }

    private static void springIocDi(ApplicationContext applicationContext) {
        AccountService accountService = applicationContext.getBean(AccountService.class);
        System.out.println("Personal info class: " + accountService.getPersonalInfoClass());
        AccountInfo accountInfoById = accountService.getAccountInfoById(1);
        System.out.println("Personal info class: " + accountService.getPersonalInfoClass());
        System.out.println(accountInfoById);

        IObject bean = applicationContext.getBean(IObject.class);
        System.out.println("object type is: " + bean.getClass());
        System.out.println("result info is: " + bean.getInfo());
    }
}
