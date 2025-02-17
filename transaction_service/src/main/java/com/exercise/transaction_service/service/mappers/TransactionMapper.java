package com.exercise.transaction_service.service.mappers;

import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.TransactionCreateDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final AccountService accountService;

    public Transaction toTransaction(TransactionCreateDTO transactionCreateDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionCreateDTO.transactionType());
        transaction.setAmount(transactionCreateDTO.amount());
        transaction.setAccount(accountService.getAccountByIdOrThrow(transactionCreateDTO.accountId()));
        return transaction;
    }

    public TransactionResponseDTO toTransactionResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getTransactionId(),
                transaction.getTransactionDate(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccount().getAccountId()
        );
    }
}
