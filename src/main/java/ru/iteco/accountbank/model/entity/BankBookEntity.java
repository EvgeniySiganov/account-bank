package ru.iteco.accountbank.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "bank_books", schema = "bank")
public class BankBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    private String number;

    @Column(precision = 8, scale = 2)
    private BigDecimal amount;

    @ManyToOne()
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;

    @OneToMany(mappedBy = "sourceBankBook")
    private Set<TransactionEntity> sourceTransactions = new HashSet<>();

    @OneToMany(mappedBy = "targetBankBook")
    private Set<TransactionEntity> targetTransactions = new HashSet<>();
}
