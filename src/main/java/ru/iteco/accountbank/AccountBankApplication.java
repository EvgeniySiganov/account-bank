package ru.iteco.accountbank;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.iteco.accountbank.model.AccountInfo;
import ru.iteco.accountbank.service.*;
import ru.iteco.accountbank.service.Process;

@ComponentScan
@PropertySource("classpath:application.properties")
public class AccountBankApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AccountBankApplication.class);
//        AccountService accountService = applicationContext.getBean(AccountService.class);
//        System.out.println("Personal info class: " + accountService.getPersonalInfoClass());
//        AccountInfo accountInfoById = accountService.getAccountInfoById(1);
//        System.out.println("Personal info class: " + accountService.getPersonalInfoClass());
//        System.out.println(accountInfoById);
//
//        IObject bean = applicationContext.getBean(IObject.class);
//        System.out.println("object type is: " + bean.getClass());
//        System.out.println("result info is: " + bean.getInfo());

//        Flow flow = applicationContext.getBean(Flow.class);
//
//        flow.run(1);
//
//        flow.run(2);
//
//        flow.run(2);
//
//        flow.run(3);
//
//        flow.run(4);
//        flow.run(4);
    }
}
