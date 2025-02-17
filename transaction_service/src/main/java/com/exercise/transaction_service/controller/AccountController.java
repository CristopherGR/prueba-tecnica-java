package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.AccountUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Set<AccountResponseDTO>> getAllAccounts() {
        log.info("REST request to get all accounts");

        Set<AccountResponseDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        log.info("REST request to get account by id: {}", accountId);

        AccountResponseDTO account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountCreateDTO accountCreateDTO) {
        log.info("REST request to create account: {}", accountCreateDTO);

        AccountResponseDTO createdAccount = accountService.createAccount(accountCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId, @RequestBody @Valid AccountUpdateDTO accountUpdateDTO) {
        log.info("REST request to update account by id: {}", accountId);

        AccountResponseDTO updatedAccount = accountService.updateAccount(accountId, accountUpdateDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        log.info("REST request to delete account by id: {}", accountId);

        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}