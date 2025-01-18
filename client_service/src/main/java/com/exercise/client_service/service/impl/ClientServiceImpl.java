package com.exercise.client_service.service.impl;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.exception.ClientNotFoundException;
import com.exercise.client_service.producer.ClientProducer;
import com.exercise.client_service.repository.ClientRepository;
import com.exercise.client_service.service.ClientService;
import com.exercise.client_service.service.dtos.ClientRequestDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.utils.IdGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final IdGeneratorService idGeneratorService;
    private final ClientProducer clientProducer;


    public ClientServiceImpl(ClientRepository clientRepository, IdGeneratorService idGeneratorService, KafkaTemplate<String, Client> kafkaTemplate, ClientProducer clientProducer) {
        this.clientRepository = clientRepository;
        this.idGeneratorService = idGeneratorService;
        this.clientProducer = clientProducer;
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        log.info("Entering ClientServiceImpl.getAllClients()");

        List<Client> clientList = clientRepository.findAll();

        return clientList.stream()
                .map(this::toClientResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO getByClientId(String clientId) {
        log.info("Entering ClientServiceImpl.getByClientId()");
        log.info("Client Id -> {}", clientId);

        Client existingClient = getClientByIdOrThrows(clientId);
        return toClientResponseDTO(existingClient);
    }

    @Override
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) {
        log.info("Entering ClientServiceImpl.createClient()");
        log.info("ClientRequestDTO -> {}", clientRequestDTO);

        Client client = toClient(clientRequestDTO);
        client.setClientId(idGeneratorService.generateUniqueClientId());
        Client savedClient = clientRepository.save(client);

        // Envio de mensaje con Kafka para crear una cuenta en el segundo microservicio y cumplir por lo menos
        // una vez con el requerimiento de establecer comunicacion asincrona entre los dos microservicios
        clientProducer.sendCreatedClient(savedClient);

        return toClientResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(String clientId, ClientRequestDTO clientRequestDTO) {
        log.info("Entering ClientServiceImpl.updateClient()");
        log.info("Client Id -> {}", clientId);
        log.info("ClientRequestDTO -> {}", clientRequestDTO);

        Client existingClient = getClientByIdOrThrows(clientId);

        if (clientRequestDTO.name() != null) existingClient.setName(clientRequestDTO.name());
        if (clientRequestDTO.personGender() != null) existingClient.setGender(clientRequestDTO.personGender());
        if (clientRequestDTO.age() != null) existingClient.setAge(clientRequestDTO.age());
        if (clientRequestDTO.address() != null) existingClient.setAddress(clientRequestDTO.address());
        if (clientRequestDTO.phone() != null) existingClient.setPhone(clientRequestDTO.phone());
        if (clientRequestDTO.password() != null) existingClient.setPassword(clientRequestDTO.password());
        if (clientRequestDTO.status() != existingClient.isStatus()) existingClient.setStatus(clientRequestDTO.status());

        Client updatedClient = clientRepository.save(existingClient);

        return toClientResponseDTO(updatedClient);
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

    private Client toClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client();
        client.setName(clientRequestDTO.name());
        client.setGender(clientRequestDTO.personGender());
        client.setAge(clientRequestDTO.age());
        client.setIdentification(clientRequestDTO.identification());
        client.setAddress(clientRequestDTO.address());
        client.setPhone(clientRequestDTO.phone());
        client.setPassword(clientRequestDTO.password());
        client.setStatus(clientRequestDTO.status());

        return client;
    }

    private ClientResponseDTO toClientResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getPersonId(),
                client.getName(),
                client.getGender(),
                client.getAge(),
                client.getIdentification(),
                client.getAddress(),
                client.getPhone(),
                client.getClientId(),
                client.getPassword(),
                client.isStatus()
        );
    }
}
