package ru.iteco.accountbank.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.iteco.accountbank.model.UserDto;
import ru.iteco.accountbank.service.UserService;
import ru.iteco.accountbank.validation.Create;
import ru.iteco.accountbank.validation.Update;

import java.util.List;

@RestController
@RequestMapping("/rest/user")
@Validated
@Slf4j
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getAllUsers(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto byId = userService.getById(id);
        ResponseCookie responseCookie = ResponseCookie.from("userId", id.toString()).maxAge(3600).build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(byId);
    }

    @PostMapping
    @Validated(Create.class)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Create user: {}", userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PutMapping
    @Validated(Update.class)
    public UserDto update(@Valid @RequestBody UserDto userDto) {
        log.info("Update user: {}", userDto);
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.delete(id);
    }
}
