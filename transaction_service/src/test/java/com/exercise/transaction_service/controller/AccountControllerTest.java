package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.domain.enums.AccountType;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest {

    private MockMvc mockMvc;
    private AccountService accountService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        accountService = mock(AccountService.class);
        AccountController accountController = new AccountController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createAccountTest() throws Exception {
        AccountCreateDTO accountCreateDTO = new AccountCreateDTO(
                AccountType.AHORROS,
                100.0,
                true,
                "CL-33409ab0e3a3478d"
        );
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO(
                1L,
                "AC-33409ab0e0000000",
                AccountType.AHORROS,
                100.0,
                true,
                "CL-33409ab0e3a3478d"
        );

        when(accountService.createAccount(Mockito.any(AccountCreateDTO.class))).thenReturn(accountResponseDTO);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(accountResponseDTO.accountNumber()))
                .andExpect(jsonPath("$.accountType").value(accountResponseDTO.accountType().toString()))
                .andExpect(jsonPath("$.initialBalance").value(accountResponseDTO.initialBalance()))
                .andExpect(jsonPath("$.status").value(accountResponseDTO.status()))
                .andExpect(jsonPath("$.clientId").value(accountResponseDTO.clientId()));

        verify(accountService, times(1)).createAccount(Mockito.any(AccountCreateDTO.class));
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        AccountResponseDTO account1 = new AccountResponseDTO(
                1L,
                "AC-33409ab0e0000000",
                AccountType.AHORROS,
                100.0,
                true,
                "CL-33409ab0e3a3478d"
        );
        AccountResponseDTO account2 = new AccountResponseDTO(
                2L,
                "AC-33409ab0e0000333",
                AccountType.CORRIENTE,
                200.0,
                true,
                "CL-33409ab0e3a34782"
        );

        Set<AccountResponseDTO> mockAccounts = new HashSet<>(Arrays.asList(account1, account2));
        Mockito.when(accountService.getAllAccounts()).thenReturn(mockAccounts);

        mockMvc.perform(get("/account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].accountId").value(1L))
                .andExpect(jsonPath("$[0].accountNumber").value("AC-33409ab0e0000000"))
                .andExpect(jsonPath("$[0].accountType").value(AccountType.AHORROS.toString()))
                .andExpect(jsonPath("$[1].accountId").value(2L))
                .andExpect(jsonPath("$[1].accountNumber").value("AC-33409ab0e0000333"))
                .andExpect(jsonPath("$[1].accountType").value(AccountType.CORRIENTE.toString()));

        verify(accountService, times(1)).getAllAccounts();
    }
}
