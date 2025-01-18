package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.AccountType;

public record AccountResponseDTO(
        Long accountId,
        String accountNumber,
        AccountType accountType,
        Double initialBalance,
        boolean status,
        String clientId
) {}

