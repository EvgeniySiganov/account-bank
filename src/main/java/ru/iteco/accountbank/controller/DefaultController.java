package ru.iteco.accountbank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.accountbank.model.ExternalInfo;
import ru.iteco.accountbank.service.ExternalService;

@RestController
public class DefaultController {
    private final ExternalService externalService;

    public DefaultController(ExternalService externalService) {
        this.externalService = externalService;
    }


    @GetMapping("/info")
    public ExternalInfo getInfo() {
        return externalService.getInfo();
    }
}
