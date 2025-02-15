package com.exercise.client_service.controller;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.service.dtos.ClientRequestDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import com.exercise.client_service.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        log.info("REST request to get all clients");

        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> getClientByClientId(@PathVariable String clientId) {
        log.info("REST request to get Client by ID: {}", clientId);

        ClientResponseDTO client = clientService.getByClientId(clientId);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        log.info("REST request to create Client: {}", clientRequestDTO);

        ClientResponseDTO createdClient = clientService.createClient(clientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable String clientId, @RequestBody ClientRequestDTO updatedClient) {
        log.info("REST request to update Client by ID: {}", clientId);

        ClientResponseDTO updated = clientService.updateClient(clientId, updatedClient);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Client> deleteClient(@PathVariable String clientId) {
        log.info("REST request to delete Client by ID: {}", clientId);

        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}
