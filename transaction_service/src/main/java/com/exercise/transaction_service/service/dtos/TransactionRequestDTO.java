package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.enums.TransactionType;

public record TransactionRequestDTO(
        TransactionType transactionType,
        Double amount,
        Double balance,
        Long accountId
) {
}
