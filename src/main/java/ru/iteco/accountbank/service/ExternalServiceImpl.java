package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;
import ru.iteco.accountbank.model.annotation.CacheResult;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@Component
@Scope("prototype")
@Slf4j
public class ExternalServiceImpl implements ExternalService {

    private Map<Integer, ExternalInfo> externalInfoMap;

    @PostConstruct
    public void init() {
        externalInfoMap = new HashMap<>();
        externalInfoMap.put(1, new ExternalInfo(1, null));
        externalInfoMap.put(2, new ExternalInfo(2, "hasInfo"));
        externalInfoMap.put(3, new ExternalInfo(3, "info"));
        externalInfoMap.put(4, new ExternalInfo(4, "information"));
        System.out.println("ExternalServiceImpl data has been filled into the map");
    }

    @PreDestroy
    public void destroy() {
        log.info("externalInfoMap before cleaning: " + externalInfoMap);
        externalInfoMap.clear();
        log.info("externalInfoMap after cleaning: " + externalInfoMap);
    }

    @CacheResult
    @Override
    public ExternalInfo getExternalInfo(Integer id) {
        ExternalInfo externalInfo = externalInfoMap.get(id);
        if (externalInfo == null) {
            throw new NoSuchElementException("There is no info by id: " + id);
        }
        System.out.println("returning external info: " + externalInfo);
        return externalInfo;
    }

    @Override
    public void saveExternalInfo(ExternalInfo externalInfo) {
        externalInfoMap.put(externalInfo.getId(), externalInfo);
    }
}
