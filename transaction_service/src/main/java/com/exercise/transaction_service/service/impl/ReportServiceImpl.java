package com.exercise.transaction_service.service.impl;

import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.exception.AccountNotFoundException;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.ReportService;
import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.AccountReportDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.ClientReportDTO;
import com.exercise.transaction_service.service.dtos.TransactionReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final AccountService accountService;
    private final TransactionService transactionService;

    public ReportServiceImpl(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public ClientReportDTO getDetailedReport(String clientId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Entering ReportServiceImpl.getDetailedReport()");
        log.info("Client Id -> {} ", clientId);
        log.info("Start Date -> {} ", startDate);
        log.info("End Date -> {} ", endDate);

        List<AccountResponseDTO> accounts = accountService.getAccountsByClientId(clientId);
        if (accounts.isEmpty()) throw new AccountNotFoundException("No Accounts found for Client: " + clientId);

        List<AccountReportDTO> accountReportDTOList = accounts.stream()
                .map(account -> {

                    List<Transaction> transactions =
                            transactionService.getByAccountIdAndDateRange(
                                    account.accountId(),
                                    startDate,
                                    endDate
                            );

                    List<TransactionReportDTO> transactionReportDTOList = transactions.stream()
                            .map(transaction -> new TransactionReportDTO(
                                    transaction.getTransactionId(),
                                    transaction.getTransactionDate(),
                                    transaction.getTransactionType(),
                                    transaction.getAmount(),
                                    transaction.getBalance()
                            ))
                            .collect(Collectors.toList());

                    return new AccountReportDTO(
                            account.accountNumber(),
                            account.initialBalance(),
                            account.status(),
                            transactionReportDTOList
                    );
                })
                .collect(Collectors.toList());

        return new ClientReportDTO(clientId, accountReportDTOList);
    }
}
