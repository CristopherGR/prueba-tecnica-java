package com.exercise.client_service.service.dtos;

import com.exercise.client_service.domain.enums.PersonGender;

public record ClientRequestDTO (
        String name,
        PersonGender personGender,
        Long age,
        String identification,
        String address,
        String phone,
        String password,
        boolean status
) {
}
