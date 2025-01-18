package com.exercise.transaction_service.service;

import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.service.dtos.ClientReportDTO;
import com.exercise.transaction_service.service.dtos.TransactionRequestDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<TransactionResponseDTO> getAllTransactions();

    TransactionResponseDTO getTransactionById(Long transactionId);

    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO);

    TransactionResponseDTO updateTransaction(Long transactionId, TransactionRequestDTO transactionRequestDTO);

    void deleteTransactionById(Long transactionId);

    Transaction getTransactionByIdOrThrows(Long transactionId);

    List<Transaction> getByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
