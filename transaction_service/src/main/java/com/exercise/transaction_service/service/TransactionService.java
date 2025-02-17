package com.exercise.transaction_service.service;

import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.service.dtos.TransactionCreateDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import com.exercise.transaction_service.service.dtos.TransactionUpdateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TransactionService {

    Set<TransactionResponseDTO> getAllTransactions();

    TransactionResponseDTO getTransactionById(Long transactionId);

    TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO);

    TransactionResponseDTO updateTransaction(Long transactionId, TransactionUpdateDTO transactionUpdateDTO);

    void deleteTransactionById(Long transactionId);

    Transaction getTransactionByIdOrThrows(Long transactionId);

    List<Transaction> getByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
