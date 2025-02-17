package com.exercise.transaction_service.domain;

import com.exercise.transaction_service.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long transactionId;

    @Column(nullable = false)
    LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @Column(nullable = false)
    Double amount;

    @Column(nullable = false)
    Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
