package com.exercise.transaction_service.repository;

import com.exercise.transaction_service.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime transactionDateAfter,
            LocalDateTime transactionDateBefore);
}
