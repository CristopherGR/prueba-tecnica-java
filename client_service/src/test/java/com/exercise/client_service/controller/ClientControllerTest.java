package com.exercise.client_service.controller;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.domain.enums.PersonGender;
import com.exercise.client_service.exception.ClientNotFoundException;
import com.exercise.client_service.exception.GlobalExceptionHandler;
import com.exercise.client_service.service.ClientService;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.dtos.ClientUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerTest {

    private MockMvc mockMvc;
    private ClientService clientService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        clientService = mock(ClientService.class);
        ClientController clientController = new ClientController(clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllClientsTest() throws Exception {
        ClientResponseDTO client1 = new ClientResponseDTO(
                1L,
                "Name 1",
                PersonGender.MALE,
                20L,
                "ID-123",
                "Address 1",
                "555-999",
                "CL-123",
                "Password",
                true
        );

        ClientResponseDTO client2 = new ClientResponseDTO(
                2L,
                "Name 2",
                PersonGender.FEMALE,
                20L,
                "ID-345",
                "Address 2",
                "555-999",
                "CL-345",
                "Password",
                false
        );

        Mockito.when(clientService.getAllClients()).thenReturn(new HashSet<>(Arrays.asList(client1, client2)));


        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].personId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Name 1"))
                .andExpect(jsonPath("$[0].personGender").value(PersonGender.MALE.toString()))
                .andExpect(jsonPath("$[0].clientId").value("CL-123"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].personId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Name 2"))
                .andExpect(jsonPath("$[1].personGender").value(PersonGender.FEMALE.toString()))
                .andExpect(jsonPath("$[1].clientId").value("CL-345"))
                .andExpect(jsonPath("$[1].status").value(false));
    }

    @Test
    public void updateClientTest() throws Exception {
        ClientUpdateDTO clientUpdateDTO = new ClientUpdateDTO(
                "Updated Name",
                PersonGender.MALE,
                20L,
                "Updated Address",
                "0999138471",
                "Updated password",
                true
        );

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO(
                1L,
                "Updated Name",
                PersonGender.MALE,
                20L,
                "0202129447",
                "Updated Address",
                "0999138471",
                "CL-123",
                "Updated password",
                true
        );

        Mockito.when(clientService.updateClient(eq("CL-123"), any(ClientUpdateDTO.class)))
                .thenReturn(clientResponseDTO);

        mockMvc.perform(put("/client/CL-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.personGender").value(PersonGender.MALE.toString()))
                .andExpect(jsonPath("$.age").value(20L))
                .andExpect(jsonPath("$.address").value("Updated Address"))
                .andExpect(jsonPath("$.phone").value("0999138471"))
                .andExpect(jsonPath("$.password").value("Updated password"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void failedUpdateClientTest() throws Exception {
        Mockito.when(clientService.updateClient(eq("CL-123"), any(ClientUpdateDTO.class)))
                .thenThrow(new ClientNotFoundException("Client with ID CL-123 not found"));

        mockMvc.perform(put("/client/CL-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Client())))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client with ID CL-123 not found"));
    }
}
