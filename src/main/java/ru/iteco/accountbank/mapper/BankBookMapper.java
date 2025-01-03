package ru.iteco.accountbank.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.model.entity.BankBookEntity;

@Mapper(componentModel = "spring")
public interface BankBookMapper {
    @Mapping(target = "currencyName", source = "currency.name")
    BankBookDto mapToDto(BankBookEntity entity);

    @Mapping(target = "currency.name", source = "currencyName")
    BankBookEntity mapToEntity(BankBookDto dto);
}
