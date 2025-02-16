package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record AccountUpdateDTO(
        AccountType accountType,

        @Min(value = 0, message = "El saldo inicial no puede ser menor a 0")
        Double initialBalance,

        boolean status,

        @Size(min = 19, max = 19, message = "El ID del cliente debe contener 19 caracteres")
        String clientId
) {
}
