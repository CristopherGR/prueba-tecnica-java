package com.exercise.transaction_service.service.dtos;

import java.util.List;

public record ClientReportDTO (
        String clientId,
        List<AccountReportDTO> accounts
){
}
