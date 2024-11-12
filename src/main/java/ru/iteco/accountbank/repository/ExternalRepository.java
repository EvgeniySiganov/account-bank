package ru.iteco.accountbank.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.User;

@Component
@Slf4j
public class ExternalRepository {
    public String getInfo(User user) {
        log.info("getInfo for user {}", user);
        return "EXTERNAL_INFO";
    }

    public void saveInfo(String info) {
        if (info == null) {
            throw new RuntimeException("Null can't be saved");
        }
        log.info("Save info {}", info);
    }
}
