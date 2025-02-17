package com.exercise.client_service.service.mappers;

import com.exercise.client_service.domain.Client;
import com.exercise.client_service.service.dtos.ClientCreateDTO;
import com.exercise.client_service.service.dtos.ClientResponseDTO;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ClientMapper {

    public Client toClient(ClientCreateDTO clientCreateDTO) {
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

    public ClientResponseDTO toClientResponseDTO(Client client) {
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
                client.getStatus()
        );
    }
}

