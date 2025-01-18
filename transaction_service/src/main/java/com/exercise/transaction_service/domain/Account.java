package com.exercise.transaction_service.domain;

import com.exercise.transaction_service.domain.enums.AccountType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Account() {
    }

    public Account(Long accountId, String accountNumber, AccountType accountType, Double initialBalance, boolean status, String clientId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.status = status;
        this.clientId = clientId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return status == account.status && Objects.equals(accountId, account.accountId) && Objects.equals(accountNumber, account.accountNumber) && accountType == account.accountType && Objects.equals(initialBalance, account.initialBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountNumber, accountType, initialBalance, status);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", initialBalance=" + initialBalance +
                ", status=" + status +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
