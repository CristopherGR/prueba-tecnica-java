package com.exercise.transaction_service.service.dtos;

import java.util.List;

public record AccountReportDTO(
        String accountNumber,
        Double currentBalance,
        boolean status,
        List<TransactionReportDTO> transactions
) {
}
