package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.TransactionType;

public record TransactionUpdateDTO(
        TransactionType transactionType,
        Double amount
) {
}
