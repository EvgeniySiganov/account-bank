package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;

@Component
@Slf4j
class ExternalServiceImpl implements ExternalService {
    @Override
    public ExternalInfo getInfo() {
        log.info("Call getInfo!");
        return ExternalInfo.builder().info("INFO").build();
    }
}
