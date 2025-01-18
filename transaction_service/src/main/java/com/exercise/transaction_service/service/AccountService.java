package com.exercise.transaction_service.service;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.service.dtos.AccountRequestDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.ClientReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {

    List<AccountResponseDTO> getAllAccounts();

    AccountResponseDTO getAccountById(Long accountId);

    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

    AccountResponseDTO updateAccount(Long accountId, AccountRequestDTO accountRequestDTO);

    void deleteAccount(Long accountId);

    Account getAccountByIdOrThrow(Long accountId);

    List<AccountResponseDTO> getAccountsByClientId(String clientId);
}
