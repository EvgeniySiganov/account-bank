package ru.iteco.accountbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.iteco.accountbank.exception.BankBookNotFoundException;
import ru.iteco.accountbank.model.BankBookDto;
import ru.iteco.accountbank.service.BankBookService;

import java.util.List;

@RestController
@RequestMapping("/bank-book")
public class BankBookRestController {
    private final BankBookService bankBookService;

    public BankBookRestController(BankBookService bankBookService) {
        this.bankBookService = bankBookService;
    }

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<BankBookDto>> getAllBankBookByUserId(@PathVariable() Integer userId) {
        List<BankBookDto> allBankBookDtoByUserId = bankBookService.getAllBankBookDtoByUserId(userId);
        return ResponseEntity.ok(allBankBookDtoByUserId);
    }

    @GetMapping("/{bankBookId}")
    public ResponseEntity<BankBookDto> getBankBookById(@PathVariable Integer bankBookId) {
        BankBookDto bankBookById = bankBookService.getBankBookById(bankBookId);
        if (bankBookById == null) {
            throw new BankBookNotFoundException();
        }
        return ResponseEntity.ok(bankBookById);
    }

    @PostMapping
    public ResponseEntity<BankBookDto> post(@RequestBody BankBookDto bankBookDto) {
        BankBookDto post = bankBookService.post(bankBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }

    @PutMapping
    public ResponseEntity<BankBookDto> put(@RequestBody BankBookDto bankBookDto) {
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
