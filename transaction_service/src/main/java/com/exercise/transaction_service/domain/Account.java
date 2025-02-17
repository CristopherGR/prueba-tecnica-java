package com.exercise.transaction_service.domain;

import com.exercise.transaction_service.domain.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long accountId;

    @Column(unique = true, nullable = false)
    String accountNumber;

    @Enumerated(EnumType.STRING)
    AccountType accountType;

    @Column(nullable = false)
    Double initialBalance;

    @Column(nullable = false)
    boolean status;

    @Column(nullable = false)
    String clientId;
}
