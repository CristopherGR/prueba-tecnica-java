package com.exercise.client_service.service;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.service.dtos.ClientRequestDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;

import java.util.List;

public interface ClientService {

    List<ClientResponseDTO> getAllClients();

    ClientResponseDTO getByClientId(String clientId);

    ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO updateClient(String clientId, ClientRequestDTO clientRequestDTO);

    void deleteClient(String clientId);

    Client getClientByIdOrThrows(String clientId);
}