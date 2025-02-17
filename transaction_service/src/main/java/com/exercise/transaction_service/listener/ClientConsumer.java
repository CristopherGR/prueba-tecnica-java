package com.exercise.transaction_service.listener;

import com.exercise.transaction_service.listener.common.ClientProducerDTO;
import com.exercise.transaction_service.domain.enums.AccountType;
import com.exercise.transaction_service.service.AccountService;
import com.exercise.transaction_service.service.dtos.AccountCreateDTO;
import com.exercise.transaction_service.service.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientConsumer {
    private static final Logger log = LoggerFactory.getLogger(ClientConsumer.class);

    private final AccountService accountService;

    @KafkaListener(topics = "clientCreatedTopic", groupId = "myGroup")
    public void createdClientTopic(String message) {
        log.info("Message received from Kafka Topic 'clientCreatedTopic' -> {}", message);

        var clientProducerDTO = JsonUtil.fromJson(message, ClientProducerDTO.class);

        log.info("Creating default account for client '{}'", clientProducerDTO.clientId());

        AccountCreateDTO accountCreateDTO = new AccountCreateDTO(
                AccountType.AHORROS,
                0.0,
                true,
                clientProducerDTO.clientId()
        );
        accountService.createAccount(accountCreateDTO);
    }
}
