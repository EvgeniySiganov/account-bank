package ru.iteco.accountbank.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.iteco.accountbank.exception.BankBookNotFoundException;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.service.BankBookService;
import ru.iteco.accountbank.validation.Create;
import ru.iteco.accountbank.validation.Update;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rest/bank-book")
@Validated
public class BankBookRestController {
    private final BankBookService bankBookService;

    public BankBookRestController(BankBookService bankBookService) {
        this.bankBookService = bankBookService;
    }

    @GetMapping("/by-user-id/")
    public ResponseEntity<List<BankBookDto>> getAllBankBookByUserId(@CookieValue Integer userId, @RequestHeader Map<String, String> headers) {
        log.info("getAllBankBookByUserId {}, with headers {}", userId, headers);
        List<BankBookDto> allBankBookDtoByUserId = bankBookService.getAllBankBookDtoByUserId(userId);
        return ResponseEntity.ok(allBankBookDtoByUserId);
    }

    @GetMapping("/{bankBookId}")
    public ResponseEntity<BankBookDto> getBankBookById(@Min(value = 0L, message = "More then 0!") @PathVariable Integer bankBookId) {
        BankBookDto bankBookById = bankBookService.getBankBookById(bankBookId);
        if (bankBookById == null) {
            throw new BankBookNotFoundException();
        }
        return ResponseEntity.ok(bankBookById);
    }


    @PostMapping
    @Validated(Create.class)
    public ResponseEntity<BankBookDto> post(@Valid @RequestBody BankBookDto bankBookDto) {
        BankBookDto post = bankBookService.post(bankBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }

    @PutMapping
    @Validated(Update.class)
    public ResponseEntity<BankBookDto> put(@Valid @RequestBody BankBookDto bankBookDto) {
        log.info("put {}", bankBookDto);
        return ResponseEntity.ok().body(bankBookService.put(bankBookDto));
    }

    @DeleteMapping("/{bankBookId}")
    public void deleteByBankBookId(@PathVariable Integer bankBookId) {
        bankBookService.deleteBankBook(bankBookId);
    }

    @DeleteMapping("by-user-id/{userId}")
    public void deleteAllByUserId(@PathVariable Integer userId) {
        bankBookService.deleteAllBankBookByUserId(userId);
    }


}
