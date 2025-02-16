package com.exercise.client_service.service;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.service.dtos.ClientCreateDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.dtos.ClientUpdateDTO;

import java.util.List;

public interface ClientService {

    List<ClientResponseDTO> getAllClients();

    ClientResponseDTO getByClientId(String clientId);

    ClientResponseDTO createClient(ClientCreateDTO clientCreateDTO);

    ClientResponseDTO updateClient(String clientId, ClientUpdateDTO clientUpdateDTO);

    void deleteClient(String clientId);

    Client getClientByIdOrThrows(String clientId);
}