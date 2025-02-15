package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountRequestDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        log.info("REST request to get all accounts");

        List<AccountResponseDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        log.info("REST request to get account by id: {}", accountId);

        AccountResponseDTO account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        log.info("REST request to create account: {}", accountRequestDTO);

        AccountResponseDTO createdAccount = accountService.createAccount(accountRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountRequestDTO accountRequestDTO) {
        log.info("REST request to update account by id: {}", accountId);

        AccountResponseDTO updatedAccount = accountService.updateAccount(accountId, accountRequestDTO);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        log.info("REST request to delete account by id: {}", accountId);

        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}