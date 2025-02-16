package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.enums.TransactionType;
import jakarta.validation.constraints.Min;

public record TransactionUpdateDTO(
        TransactionType transactionType,
        Double amount
) {
}
