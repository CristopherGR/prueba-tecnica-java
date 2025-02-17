package com.exercise.transaction_service.service.mappers;

import com.exercise.transaction_service.domain.Account;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.utils.IdGeneratorUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountCreateDTO accountCreateDTO) {
        Account account = new Account();
        account.setAccountNumber(IdGeneratorUtil.generateUniqueAccountNumber());
        account.setAccountType(accountCreateDTO.accountType());
        account.setInitialBalance(accountCreateDTO.initialBalance());
        account.setStatus(accountCreateDTO.status());
        account.setClientId(accountCreateDTO.clientId());
        return account;
    }

    public AccountResponseDTO toAccountResponseDTO(Account account) {
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
