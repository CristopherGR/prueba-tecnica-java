package com.exercise.transaction_service.service.dtos;

import com.exercise.transaction_service.domain.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreateDTO(
        @NotNull(message = "El tipo de cuenta es olbigatorio")
        AccountType accountType,

        @NotNull(message = "El saldo inicial debe ser establecido")
        @Min(value = 0, message = "El saldo inicial no puede ser menor a 0")
        Double initialBalance,

        @NotNull(message = "El estado de la cuenta debe ser establecido")
        boolean status,

        @NotBlank(message = "El ID del cliente es obligatorio para asociarlo a la nueva cuenta")
        @Size(min = 19, max = 19, message = "El ID del cliente debe contener 19 caracteres")
        String clientId
) {
}
