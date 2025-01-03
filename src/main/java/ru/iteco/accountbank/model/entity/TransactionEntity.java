package ru.iteco.accountbank.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", schema = "bank")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "source_bank_book_id")
    private BankBookEntity sourceBankBook;

    @ManyToOne()
    @JoinColumn(name = "target_bank_book_id")
    private BankBookEntity targetBankBook;

    private BigDecimal amount;

    private LocalDateTime initiationDate;

    private LocalDateTime completionDate;

    @ManyToOne()
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusEntity status;
}
