package ru.iteco.accountbank.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import ru.iteco.accountbank.model.entity.BankBookEntity;

import java.util.List;
import java.util.Optional;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {
    List<BankBookEntity> findBankBookEntityByUserId(Integer userId);
    List<BankBookEntity> findBankBookEntityByUserIdAndNumberAndCurrencyId(Integer userId, String number, Integer currencyId);
    void deleteAllByUserId(Integer userId);
    BankBookEntity findBankBookEntityById(Integer id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select bbe from BankBookEntity bbe where bbe.number = :id")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    Optional<BankBookEntity> lockById(@Param("id") Integer id);
}
