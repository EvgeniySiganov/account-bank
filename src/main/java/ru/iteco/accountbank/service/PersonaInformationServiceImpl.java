package ru.iteco.accountbank.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.PersonalInfo;

import javax.annotation.PostConstruct;

@Service
@Lazy
public class PersonaInformationServiceImpl implements PersonaInformationService {

    @Value("${name}")
    private String name;

    public PersonaInformationServiceImpl() {
    }

    @PostConstruct
    public void init(){
        if (name.contains("N")){
            System.out.println("Contains 'N'");
        }
    }

    public PersonalInfo getPersonalInfoById(Integer id){
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName(name);
        personalInfo.setUserId(id);
        return personalInfo;
    }
}
