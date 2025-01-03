package ru.iteco.accountbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.accountbank.model.entity.CurrencyEntity;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

    CurrencyEntity findCurrencyEntityByName(String name);
    boolean existsByName(String name);
}
