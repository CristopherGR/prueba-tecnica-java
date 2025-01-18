package com.exercise.transaction_service.service.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGeneratorService {
    public String generateUniqueAccountNumber() {
        return "AC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}

