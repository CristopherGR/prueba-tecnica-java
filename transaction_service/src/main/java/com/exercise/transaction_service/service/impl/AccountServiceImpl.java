package com.exercise.transaction_service.service.impl;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.exception.AccountNotFoundException;
import com.exercise.transaction_service.repository.AccountRepository;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.AccountUpdateDTO;
import com.exercise.transaction_service.service.utils.IdGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final IdGeneratorService idGeneratorService;

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        log.info("Entering AccountServiceImpl.getAllAccounts()");

        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(this::toAccountResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getAccountById(Long accountId) {
        log.info("Entering AccountServiceImpl.getAccountById()");
        log.info("Account Id -> {} ", accountId);

        Account account = getAccountByIdOrThrow(accountId);
        return toAccountResponseDTO(account);
    }

    @Override
    public AccountResponseDTO createAccount(AccountCreateDTO accountCreateDTO) {
        log.info("Entering AccountServiceImpl.createAccount()");
        log.info("AccountRequestDTO -> {} ", accountCreateDTO);

        Account account = toAccount(accountCreateDTO);
        Account savedAccount = accountRepository.save(account);

        return toAccountResponseDTO(savedAccount);
    }

    @Override
    public AccountResponseDTO updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO) {
        log.info("Entering AccountServiceImpl.updateAccount()");
        log.info("Account Id -> {} ", accountId);
        log.info("AccountRequestDTO -> {} ", accountUpdateDTO);

        Account account = getAccountByIdOrThrow(accountId);
        if (accountUpdateDTO.accountType() != null) account.setAccountType(accountUpdateDTO.accountType());
        if (accountUpdateDTO.initialBalance() != null) account.setInitialBalance(accountUpdateDTO.initialBalance());
        if (accountUpdateDTO.status() != account.isStatus()) account.setStatus(accountUpdateDTO.status());

        Account updatedAccount = accountRepository.save(account);

        return toAccountResponseDTO(updatedAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        log.info("Entering AccountServiceImpl.deleteAccount()");
        log.info("Account Id -> {} ", accountId);

        Account account = getAccountByIdOrThrow(accountId);
        accountRepository.delete(account);
    }

    @Override
    public Account getAccountByIdOrThrow(Long accountId) {
        log.info("Entering AccountServiceImpl.getAccountByIdOrThrow()");
        log.info("Account Id -> {} ", accountId);

        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta " + accountId + " no encontrada"));
    }

    @Override
    public List<AccountResponseDTO> getAccountsByClientId(String clientId) {
        log.info("Entering AccountServiceImpl.getAccountsByClientId()");
        log.info("Client Id -> {} ", clientId);

        List<Account> accountList = accountRepository.findAllByClientId(clientId);

        return accountList.stream()
                .map(this::toAccountResponseDTO)
                .collect(Collectors.toList());
    }

    private Account toAccount(AccountCreateDTO accountCreateDTO) {
        Account account = new Account();
        account.setAccountNumber(idGeneratorService.generateUniqueAccountNumber());
        account.setAccountType(accountCreateDTO.accountType());
        account.setInitialBalance(accountCreateDTO.initialBalance());
        account.setStatus(accountCreateDTO.status());
        account.setClientId(accountCreateDTO.clientId());
        return account;
    }

    private AccountResponseDTO toAccountResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.isStatus(),
                account.getClientId()
        );
    }
}
