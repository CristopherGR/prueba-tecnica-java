package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long transactionId,
        LocalDateTime transactionDate,
        TransactionType transactionType,
        Double amount,
        Double balance,
        Long accountId
) {
}

