package ru.iteco.accountbank.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.ExternalInfo;

import java.util.Objects;

@Service
public class ExternalInfoProcess implements Process {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @Override
    public boolean run(ExternalInfo externalInfo) {
        if (!Objects.equals(externalInfo.getId(), idNotProcess)) {
            System.out.println("ExternalInfoProcess id: " + externalInfo.getId() + " process");
            return true;
        } else {
            System.out.println("ExternalInfoProcess id: " + externalInfo.getId() + " is not process");
            return false;
        }
    }
}
