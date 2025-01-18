package com.exercise.transaction_service.controller;

import com.exercise.transaction_service.domain.enums.AccountType;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountRequestDTO;
import com.exercise.transaction_service.service.dtos.AccountResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

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
    public void getAllAccountsTest() throws Exception {
        AccountResponseDTO account1 = new AccountResponseDTO(
                1L,
                "AC-123",
                AccountType.AHORROS,
                100.0,
                true,
                "CL-1"
        );
        AccountResponseDTO account2 = new AccountResponseDTO(
                2L,
                "AC-456",
                AccountType.CORRIENTE,
                200.0,
                true,
                "CL-2"
        );

        List<AccountResponseDTO> mockAccounts = Arrays.asList(account1, account2);
        Mockito.when(accountService.getAllAccounts()).thenReturn(mockAccounts);

        mockMvc.perform(get("/account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].accountId").value(1L))
                .andExpect(jsonPath("$[0].accountNumber").value("AC-123"))
                .andExpect(jsonPath("$[0].accountType").value(AccountType.AHORROS.toString()))
                .andExpect(jsonPath("$[1].accountId").value(2L))
                .andExpect(jsonPath("$[1].accountNumber").value("AC-456"))
                .andExpect(jsonPath("$[1].accountType").value(AccountType.CORRIENTE.toString()));

        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    public void createAccountTest() throws Exception {
        AccountRequestDTO accountRequest = new AccountRequestDTO(
                "AC-123",
                AccountType.AHORROS,
                100.0,
                true,
                "CL-1"
        );
        AccountResponseDTO createdAccount = new AccountResponseDTO(
                1L,
                "AC-123",
                AccountType.AHORROS,
                100.0,
                true,
                "CL-1"
        );

        when(accountService.createAccount(Mockito.any(AccountRequestDTO.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("AC-123"))
                .andExpect(jsonPath("$.accountType").value(AccountType.AHORROS.toString()));

        verify(accountService, times(1)).createAccount(Mockito.any(AccountRequestDTO.class));
    }
}
