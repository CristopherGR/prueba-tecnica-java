package com.exercise.client_service.service.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

public class IdGeneratorUtil {
    public static String generateUniqueClientId() {
        return "CL-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}

