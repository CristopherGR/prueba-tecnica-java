package com.exercise.client_service.service.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGeneratorService {
    public String generateUniqueClientId() {
        return "CL-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}

