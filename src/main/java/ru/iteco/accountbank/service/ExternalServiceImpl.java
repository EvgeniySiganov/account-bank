package ru.iteco.accountbank.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.ExternalInfo;
import ru.iteco.accountbank.model.annotation.CacheResult;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
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

    //@SneakyThrows
    @PreDestroy
    public void deleteData() {
        externalInfoMap.clear();
        System.out.println("ExternalServiceImpl data has been deleted from the map");
        //Thread.sleep(500);
    }

    @CacheResult
    @Override
    public ExternalInfo getExternalInfo(Integer id) {
        ExternalInfo externalInfo = externalInfoMap.get(id);
        System.out.println("returning external info: " + externalInfo);
        return externalInfo;
    }
}
