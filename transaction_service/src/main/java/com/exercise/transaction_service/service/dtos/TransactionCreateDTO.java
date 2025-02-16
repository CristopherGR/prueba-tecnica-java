package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

public record TransactionCreateDTO(
        @NotNull(message = "El tipo de transacción es obligatorio")
        TransactionType transactionType,

        @NotNull(message = "El valor de la transacción es obligatorio")
        Double amount,

        @NotNull(message = "El ID de cuenta es obligatorio")
        Long accountId
) {
}
