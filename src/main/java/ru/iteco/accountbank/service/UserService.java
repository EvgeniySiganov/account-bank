package ru.iteco.accountbank.service;

import ru.iteco.accountbank.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto getById(Integer id);
    UserDto create(UserDto userDto);
    UserDto update(UserDto userDto);
    void delete(Integer id);
}
