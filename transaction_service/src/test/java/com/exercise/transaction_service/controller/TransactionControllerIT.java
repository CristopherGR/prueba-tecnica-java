package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.domain.enums.AccountType;
import com.exercise.transaction_service.enums.TransactionType;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.TransactionService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.exercise.transaction_service.service.dtos.TransactionCreateDTO;
import com.exercise.transaction_service.service.dtos.TransactionResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerIT {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllTransactionsIT() throws Exception {

        AccountCreateDTO accountRequest = new AccountCreateDTO(
                AccountType.AHORROS,
                100.0,
                true,
                "CL-1"
        );
        AccountResponseDTO accountResponse = accountService.createAccount(accountRequest);

        TransactionCreateDTO transactionCreateDTO1 = new TransactionCreateDTO(
                TransactionType.CREDITO,
                100.0,
                accountResponse.accountId()
        );
        TransactionResponseDTO transactionResponseDTO1 = transactionService.createTransaction(transactionCreateDTO1);

        TransactionCreateDTO transactionCreateDTO2 = new TransactionCreateDTO(
                TransactionType.DEBITO,
                -50.0,
                accountResponse.accountId()
        );
        TransactionResponseDTO transactionResponseDTO2 = transactionService.createTransaction(transactionCreateDTO2);

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].transactionId").value(transactionResponseDTO1.transactionId()))
                .andExpect(jsonPath("$[0].transactionType").value(TransactionType.CREDITO.toString()))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].accountId").value(accountResponse.accountId()))
                .andExpect(jsonPath("$[1].transactionId").value(transactionResponseDTO2.transactionId()))
                .andExpect(jsonPath("$[1].transactionType").value(TransactionType.DEBITO.toString()))
                .andExpect(jsonPath("$[1].amount").value(-50.0))
                .andExpect(jsonPath("$[1].accountId").value(accountResponse.accountId()));
    }
}
