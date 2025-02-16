package com.exercise.client_service.service.impl;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.exception.ClientNotFoundException;
import com.exercise.client_service.producer.ClientProducer;
import com.exercise.client_service.repository.ClientRepository;
import com.exercise.client_service.service.ClientService;
import com.exercise.client_service.service.dtos.ClientCreateDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.dtos.ClientUpdateDTO;
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
    public ClientResponseDTO createClient(ClientCreateDTO clientCreateDTO) {
        log.info("Entering ClientServiceImpl.createClient()");
        log.info("ClientRequestDTO -> {}", clientCreateDTO);

        Client client = toClient(clientCreateDTO);
        client.setClientId(idGeneratorService.generateUniqueClientId());
        Client savedClient = clientRepository.save(client);

        // Envio de mensaje con Kafka para crear una cuenta en el segundo microservicio y cumplir por lo menos
        // una vez con el requerimiento de establecer comunicacion asincrona entre los dos microservicios
        clientProducer.sendCreatedClient(savedClient);

        return toClientResponseDTO(savedClient);
    }

    @Override
    public ClientResponseDTO updateClient(String clientId, ClientUpdateDTO clientUpdateDTO) {
        log.info("Entering ClientServiceImpl.updateClient()");
        log.info("Client Id -> {}", clientId);
        log.info("ClientRequestDTO -> {}", clientUpdateDTO);

        Client existingClient = getClientByIdOrThrows(clientId);

        if (clientUpdateDTO.name() != null) existingClient.setName(clientUpdateDTO.name());
        if (clientUpdateDTO.personGender() != null) existingClient.setGender(clientUpdateDTO.personGender());
        if (clientUpdateDTO.age() != null) existingClient.setAge(clientUpdateDTO.age());
        if (clientUpdateDTO.address() != null) existingClient.setAddress(clientUpdateDTO.address());
        if (clientUpdateDTO.phone() != null) existingClient.setPhone(clientUpdateDTO.phone());
        if (clientUpdateDTO.password() != null) existingClient.setPassword(clientUpdateDTO.password());
        if (clientUpdateDTO.status() != existingClient.isStatus()) existingClient.setStatus(clientUpdateDTO.status());

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

    private Client toClient(ClientCreateDTO clientCreateDTO) {
        Client client = new Client();
        client.setName(clientCreateDTO.name());
        client.setGender(clientCreateDTO.personGender());
        client.setAge(clientCreateDTO.age());
        client.setIdentification(clientCreateDTO.identification());
        client.setAddress(clientCreateDTO.address());
        client.setPhone(clientCreateDTO.phone());
        client.setPassword(clientCreateDTO.password());
        client.setStatus(clientCreateDTO.status());

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
