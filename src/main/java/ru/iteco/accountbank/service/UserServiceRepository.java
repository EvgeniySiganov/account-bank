package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.accountbank.model.AddressDto;
import ru.iteco.accountbank.model.entity.AddressEntity;
import ru.iteco.accountbank.model.entity.UserEntity;
import ru.iteco.accountbank.model.UserDto;
import ru.iteco.accountbank.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class UserServiceRepository implements UserService {

    private final UserRepository userRepository;

    public UserServiceRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserServiceRepository::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Integer id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            log.info("user from repository: {}", byId.get());
            AddressEntity address = byId.get().getAddress();
            log.info("user from address: {}", address.getUser());
            log.info("Groups from user: {}", byId.get().getGroups());
        }
        byId = userRepository.findById(id);

        return toUserDto(Objects.requireNonNull(byId.orElse(null)));
    }

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = toUserEntity(userDto);
        log.info("user-entity before save: {}", userEntity);
        userEntity = userRepository.saveAndFlush(userEntity);
        log.info("user-entity after save: {}", userEntity);
        return toUserDto(userEntity);
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("There is no such user"));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        AddressDto addressDto = userDto.getAddress();
        AddressEntity addressEntity = userEntity.getAddress();
        if (addressDto != null && addressEntity != null) {
            addressEntity.setCountry(addressDto.getCountry());
            addressEntity.setCity(addressDto.getCity());
            addressEntity.setStreet(addressDto.getStreet());
            addressEntity.setHome(addressDto.getHome());
        }
        return toUserDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("There is no such user"));
        log.info("user-entity before delete: {}", userEntity);
        userRepository.delete(userEntity);
        log.info("user-entity after delete: {}", userEntity);
    }

    private static UserDto toUserDto(UserEntity user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .address(user.getAddress() != null ? AddressDto.builder()
                            .country(user.getAddress().getCountry())
                            .city(user.getAddress().getCity())
                            .street(user.getAddress().getStreet())
                            .home(user.getAddress().getHome())
                            .build()
                        : null)
                .build();
    }

    private static UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .id(userDto.getId())
                .build();

        if (userDto.getAddress() != null) {
            AddressEntity addressEntity = AddressEntity.builder()
                    .country(userDto.getAddress().getCountry())
                    .city(userDto.getAddress().getCity())
                    .street(userDto.getAddress().getStreet())
                    .home(userDto.getAddress().getHome())
                    .build();
            userEntity.setAddress(addressEntity);
        }
        return userEntity;
    }
}
