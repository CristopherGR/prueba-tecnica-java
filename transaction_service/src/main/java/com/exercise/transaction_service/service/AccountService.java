package com.exercise.transaction_service.service;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.AccountUpdateDTO;

import java.util.List;

public interface AccountService {

    List<AccountResponseDTO> getAllAccounts();

    AccountResponseDTO getAccountById(Long accountId);

    AccountResponseDTO createAccount(AccountCreateDTO accountCreateDTO);

    AccountResponseDTO updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO);

    void deleteAccount(Long accountId);

    Account getAccountByIdOrThrow(Long accountId);

    List<AccountResponseDTO> getAccountsByClientId(String clientId);
}
