package com.exercise.client_service.service.impl;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.exception.ClientNotFoundException;
import com.exercise.client_service.producer.ClientProducer;
import com.exercise.client_service.repository.ClientRepository;
import com.exercise.client_service.service.ClientService;
import com.exercise.client_service.service.dtos.ClientCreateDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.dtos.ClientUpdateDTO;
import com.exercise.client_service.service.mappers.ClientMapper;
import com.exercise.client_service.service.utils.IdGeneratorUtil;
import com.exercise.client_service.service.utils.UpdateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final ClientProducer clientProducer;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientResponseDTO> getAllClients() {
        log.info("Entering ClientServiceImpl.getAllClients()");

        List<Client> clientList = clientRepository.findAll();

        return clientList.stream()
                .map(clientMapper::toClientResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO getByClientId(String clientId) {
        log.info("Entering ClientServiceImpl.getByClientId()");
        log.info("Client Id -> {}", clientId);

        Client existingClient = getClientByIdOrThrows(clientId);
        return clientMapper.toClientResponseDTO(existingClient);
    }

    @Override
    public ClientResponseDTO createClient(ClientCreateDTO clientCreateDTO) {
        log.info("Entering ClientServiceImpl.createClient()");
        log.info("ClientRequestDTO -> {}", clientCreateDTO);

        Client client = clientMapper.toClient(clientCreateDTO);
        client.setClientId(IdGeneratorUtil.generateUniqueClientId());
        Client savedClient = clientRepository.save(client);

        // Envio de mensaje con Kafka para crear una cuenta en el segundo microservicio y cumplir por lo menos
        // una vez con el requerimiento de establecer comunicacion asincrona entre los dos microservicios
        clientProducer.sendCreatedClient(savedClient);

        return clientMapper.toClientResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(String clientId, ClientUpdateDTO clientUpdateDTO) {
        log.info("Entering ClientServiceImpl.updateClient()");
        log.info("Client Id -> {}", clientId);
        log.info("ClientRequestDTO -> {}", clientUpdateDTO);

        Client existingClient = getClientByIdOrThrows(clientId);

        UpdateUtil.updateIfNotNull(clientUpdateDTO.name(), existingClient::setName);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.personGender(), existingClient::setGender);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.age(), existingClient::setAge);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.address(), existingClient::setAddress);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.phone(), existingClient::setPhone);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.password(), existingClient::setPassword);
        UpdateUtil.updateIfNotNull(clientUpdateDTO.status(), existingClient::setStatus);

        Client updatedClient = clientRepository.save(existingClient);

        return clientMapper.toClientResponseDTO(updatedClient);
    }

    @Override
    public void deleteClient(String clientId) {
        log.info("Entering ClientServiceImpl.deleteClient()");
        log.info("Client Id -> {}", clientId);

        Client existingClient = getClientByIdOrThrows(clientId);
        clientRepository.delete(existingClient);
    }

    @Override
    public Client getClientByIdOrThrows(String clientId) {
        log.info("Entering ClientServiceImpl.getClientByIdOrThrows()");
        log.info("Client Id -> {}", clientId);

        return clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + clientId + " not found"));
    }
}
