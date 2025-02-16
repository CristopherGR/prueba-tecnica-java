package com.exercise.client_service.controller;

import com.exercise.client_service.domain.enums.PersonGender;
import com.exercise.client_service.service.dtos.ClientCreateDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientControllerIT {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void getAllClientsTest() throws Exception {
        ClientCreateDTO client1 = new ClientCreateDTO(
                "Name 1",
                PersonGender.MALE,
                20L,
                "Address 1",
                "555-999",
                "CL-123",
                "Password",
                true
        );
        ClientResponseDTO firstClientAdded = clientService.createClient(client1);

        ClientCreateDTO client2 = new ClientCreateDTO(
                "Name 2",
                PersonGender.FEMALE,
                20L,
                "Address 2",
                "555-999",
                "CL-345",
                "Password",
                false
        );
        ClientResponseDTO secondClientAdded = clientService.createClient(client2);

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].personId").value(firstClientAdded.personId()))
                .andExpect(jsonPath("$[0].name").value(firstClientAdded.name()))
                .andExpect(jsonPath("$[0].personGender").value(firstClientAdded.personGender().toString()))
                .andExpect(jsonPath("$[0].clientId").value(firstClientAdded.clientId()))
                .andExpect(jsonPath("$[0].status").value(firstClientAdded.status()))
                .andExpect(jsonPath("$[1].personId").value(secondClientAdded.personId()))
                .andExpect(jsonPath("$[1].name").value(secondClientAdded.name()))
                .andExpect(jsonPath("$[1].personGender").value(secondClientAdded.personGender().toString()))
                .andExpect(jsonPath("$[1].clientId").value(secondClientAdded.clientId()))
                .andExpect(jsonPath("$[1].status").value(secondClientAdded.status()));
    }
}
