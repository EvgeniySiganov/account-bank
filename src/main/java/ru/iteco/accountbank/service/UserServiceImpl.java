package ru.iteco.accountbank.service;

import org.springframework.stereotype.Service;
import ru.iteco.accountbank.model.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {
    private final Map<Integer, UserDto> userDtoMap = new ConcurrentHashMap<>();
    private final AtomicInteger sequenceId = new AtomicInteger(1);


    @Override
    public List<UserDto> getAll() {
        return new ArrayList<>(userDtoMap.values());
    }

    @Override
    public UserDto getById(Integer id) {
        return userDtoMap.get(id);
    }

    @Override
    public UserDto create(UserDto userDto) {
        int id = sequenceId.getAndIncrement();
        userDto.setId(id);
        userDtoMap.put(id, userDto);
        return userDto;
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserDto userDtoBefore = userDtoMap.get(userDto.getId());
        if (userDtoBefore == null) {
            return null;
        } else {
            userDtoMap.put(userDto.getId(), userDto);
            return userDto;
        }
    }

    @Override
    public void delete(Integer id) {
        userDtoMap.remove(id);
    }
}
