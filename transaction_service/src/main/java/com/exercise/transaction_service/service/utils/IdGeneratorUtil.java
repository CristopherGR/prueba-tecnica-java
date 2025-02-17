package com.exercise.transaction_service.service.utils;

import java.util.UUID;

public class IdGeneratorUtil {

    // Devuelve un identificador para ser utilizado como numero de cuenta
    public static String generateUniqueAccountNumber() {
        return "AC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}

