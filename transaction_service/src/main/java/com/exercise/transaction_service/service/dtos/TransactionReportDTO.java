package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionReportDTO(
        Long transactionId,
        LocalDateTime transactionDate,
        TransactionType transactionType,
        Double amount,
        Double resultingBalance
) {
}
