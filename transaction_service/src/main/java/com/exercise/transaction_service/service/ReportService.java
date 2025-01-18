package com.exercise.transaction_service.service;

import com.exercise.transaction_service.service.dtos.ClientReportDTO;

import java.time.LocalDateTime;

public interface ReportService {

    ClientReportDTO getDetailedReport(String clientId, LocalDateTime startDate, LocalDateTime endDate);

}
