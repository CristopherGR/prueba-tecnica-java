package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.TransactionRequestDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        log.info("REST request to get all transactions");

        List<TransactionResponseDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long transactionId) {
        log.info("REST request to get transaction by id: {}", transactionId);

        TransactionResponseDTO transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        log.info("REST request to create transaction: {}", transactionRequestDTO);

        TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        log.info("REST request to update transaction by id: {}", transactionId);

        TransactionResponseDTO updatedTransaction = transactionService.updateTransaction(transactionId, transactionRequestDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        log.info("REST request to delete transaction by id: {}", transactionId);

        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.noContent().build();
    }
}
