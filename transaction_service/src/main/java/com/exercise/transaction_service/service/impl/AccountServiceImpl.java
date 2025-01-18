package com.exercise.transaction_service.service.impl;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.exception.AccountNotFoundException;
import com.exercise.transaction_service.repository.AccountRepository;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountRequestDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.utils.IdGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final IdGeneratorService idGeneratorService;

    public AccountServiceImpl(AccountRepository accountRepository, IdGeneratorService idGeneratorService) {
        this.accountRepository = accountRepository;
        this.idGeneratorService = idGeneratorService;
    }

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
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        log.info("Entering AccountServiceImpl.createAccount()");
        log.info("AccountRequestDTO -> {} ", accountRequestDTO);

        Account account = toAccount(accountRequestDTO);
        Account savedAccount = accountRepository.save(account);

        return toAccountResponseDTO(savedAccount);
    }

    @Override
    public AccountResponseDTO updateAccount(Long accountId, AccountRequestDTO accountRequestDTO) {
        log.info("Entering AccountServiceImpl.updateAccount()");
        log.info("Account Id -> {} ", accountId);
        log.info("AccountRequestDTO -> {} ", accountRequestDTO);

        Account account = getAccountByIdOrThrow(accountId);
        if (accountRequestDTO.accountType() != null) account.setAccountType(accountRequestDTO.accountType());
        if (accountRequestDTO.initialBalance() != null) account.setInitialBalance(accountRequestDTO.initialBalance());
        if (accountRequestDTO.status() != account.isStatus()) account.setStatus(accountRequestDTO.status());

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

    private Account toAccount(AccountRequestDTO accountRequestDTO) {
        Account account = new Account();
        account.setAccountNumber(idGeneratorService.generateUniqueAccountNumber());
        account.setAccountType(accountRequestDTO.accountType());
        account.setInitialBalance(accountRequestDTO.initialBalance());
        account.setStatus(accountRequestDTO.status());
        account.setClientId(accountRequestDTO.clientId());
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
