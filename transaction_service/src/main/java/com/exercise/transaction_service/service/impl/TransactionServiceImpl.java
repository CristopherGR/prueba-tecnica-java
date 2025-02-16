package com.exercise.transaction_service.service.impl;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.exception.AccountNotFoundException;
import com.exercise.transaction_service.exception.UnavailableBalanceException;
import com.exercise.transaction_service.repository.AccountRepository;
import com.exercise.transaction_service.repository.TransactionRepository;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountUpdateDTO;
import com.exercise.transaction_service.service.dtos.TransactionRequestDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        log.info("Entering TransactionServiceImpl.getAllTransactions()");

        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(this::toTransactionResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO getTransactionById(Long transactionId) {
        log.info("Entering TransactionServiceImpl.getTransactionById()");
        log.info("Transaction Id -> {} ", transactionId);

        Transaction transaction = getTransactionByIdOrThrows(transactionId);

        return toTransactionResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        log.info("Entering TransactionServiceImpl.createTransaction()");
        log.info("TransactionRequestDTO -> {} ", transactionRequestDTO);

        Account associatedAccount = accountService.getAccountByIdOrThrow(transactionRequestDTO.accountId());
        Double currentBalance = associatedAccount.getInitialBalance();

        Double newBalance = currentBalance + transactionRequestDTO.amount();
        if (newBalance < 0) throw new UnavailableBalanceException("Saldo insuficiente para realizar el movimiento");

        Transaction transaction = toTransaction(transactionRequestDTO);
        transaction.setBalance(newBalance);
        Transaction savedTransaction = transactionRepository.save(transaction);

        associatedAccount.setInitialBalance(newBalance);
        accountService.updateAccount(associatedAccount.getAccountId(), new AccountUpdateDTO(
                associatedAccount.getAccountType(),
                associatedAccount.getInitialBalance(),
                associatedAccount.isStatus(),
                associatedAccount.getClientId()
        ));

        return toTransactionResponseDTO(savedTransaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(Long transactionId, TransactionRequestDTO transactionRequestDTO) {
        log.info("Entering TransactionServiceImpl.updateTransaction()");
        log.info("Transaction Id -> {} ", transactionId);
        log.info("TransactionRequestDTO -> {} ", transactionRequestDTO);

        Transaction transaction = getTransactionByIdOrThrows(transactionId);
        if (transactionRequestDTO.transactionType() != null) transaction.setTransactionType(transactionRequestDTO.transactionType());
        if (transactionRequestDTO.amount() != null) transaction.setAmount(transactionRequestDTO.amount());
        if (transactionRequestDTO.balance() != null) transaction.setBalance(transactionRequestDTO.balance());

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return toTransactionResponseDTO(updatedTransaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        log.info("Entering TransactionServiceImpl.deleteTransactionById()");
        log.info("Transaction Id -> {} ", transactionId);

        Transaction transaction = getTransactionByIdOrThrows(transactionId);
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction getTransactionByIdOrThrows(Long transactionId) {
        log.info("Entering TransactionServiceImpl.getTransactionByIdOrThrows()");
        log.info("Transaction Id -> {} ", transactionId);

        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new AccountNotFoundException("Movimiento " + transactionId + " no encontrado"));
    }

    @Override
    public List<Transaction> getByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Entering TransactionServiceImpl.getByAccountIdAndDateRange()");
        log.info("Account Id -> {} ", accountId);
        log.info("Start Date -> {} ", startDate);
        log.info("End Date -> {} ", endDate);

        return transactionRepository.findByAccount_AccountIdAndTransactionDateBetween(accountId, startDate, endDate);
    }

    private Transaction toTransaction(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionRequestDTO.transactionType());
        transaction.setAmount(transactionRequestDTO.amount());
        transaction.setAccount(accountService.getAccountByIdOrThrow(transactionRequestDTO.accountId()));
        return transaction;
    }

    private TransactionResponseDTO toTransactionResponseDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getTransactionId(),
                transaction.getTransactionDate(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccount().getAccountId()
        );
    }
}
