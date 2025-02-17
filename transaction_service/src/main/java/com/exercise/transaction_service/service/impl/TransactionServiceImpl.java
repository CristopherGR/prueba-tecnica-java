package com.exercise.transaction_service.service.impl;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.domain.Transaction;
import com.exercise.transaction_service.exception.AccountNotFoundException;
import com.exercise.transaction_service.exception.UnavailableBalanceException;
import com.exercise.transaction_service.repository.TransactionRepository;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.AccountUpdateDTO;
import com.exercise.transaction_service.service.dtos.TransactionCreateDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import com.exercise.transaction_service.service.dtos.TransactionUpdateDTO;
import com.exercise.transaction_service.service.mappers.TransactionMapper;
import com.exercise.transaction_service.service.utils.UpdateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        log.info("Entering TransactionServiceImpl.getAllTransactions()");

        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transactionMapper::toTransactionResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO getTransactionById(Long transactionId) {
        log.info("Entering TransactionServiceImpl.getTransactionById()");
        log.info("Transaction Id -> {} ", transactionId);

        Transaction transaction = getTransactionByIdOrThrows(transactionId);

        return transactionMapper.toTransactionResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {
        log.info("Entering TransactionServiceImpl.createTransaction()");
        log.info("TransactionRequestDTO -> {} ", transactionCreateDTO);

        // Se crea la transaccion con una validacion previa del saldo actual
        Account associatedAccount = accountService.getAccountByIdOrThrow(transactionCreateDTO.accountId());

        Double currentBalance = associatedAccount.getInitialBalance();
        Double newBalance = currentBalance + transactionCreateDTO.amount();
        if (newBalance < 0) throw new UnavailableBalanceException("Saldo insuficiente para realizar el movimiento");

        Transaction transaction = transactionMapper.toTransaction(transactionCreateDTO);
        transaction.setBalance(newBalance);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Se actualiza el saldo actual en la cuenta en base a la transaccion anterior
        associatedAccount.setInitialBalance(newBalance);
        accountService.updateAccount(associatedAccount.getAccountId(), new AccountUpdateDTO(
                associatedAccount.getAccountType(),
                associatedAccount.getInitialBalance(),
                associatedAccount.isStatus(),
                associatedAccount.getClientId()
        ));

        return transactionMapper.toTransactionResponseDTO(savedTransaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(Long transactionId, TransactionUpdateDTO transactionUpdateDTO) {
        log.info("Entering TransactionServiceImpl.updateTransaction()");
        log.info("Transaction Id -> {} ", transactionId);
        log.info("TransactionRequestDTO -> {} ", transactionUpdateDTO);

        Transaction existingTransaction = getTransactionByIdOrThrows(transactionId);
        UpdateUtil.updateIfNotNull(transactionUpdateDTO.transactionType(), existingTransaction::setTransactionType);
        UpdateUtil.updateIfNotNull(transactionUpdateDTO.amount(), existingTransaction::setAmount);

        Transaction updatedTransaction = transactionRepository.save(existingTransaction);

        return transactionMapper.toTransactionResponseDTO(updatedTransaction);
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
}
