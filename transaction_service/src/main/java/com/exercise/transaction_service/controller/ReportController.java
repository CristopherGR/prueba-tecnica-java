package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.service.ReportService;
import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.ClientReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/report")
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    public ReportController(TransactionService transactionService, ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ClientReportDTO> getReportByClientIdAndDateRange(
            @RequestParam String clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("REST request to get report by clientId: {} and dateRange: {} - {}", clientId, startDate, endDate);

        ClientReportDTO report = reportService.getDetailedReport(clientId, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
