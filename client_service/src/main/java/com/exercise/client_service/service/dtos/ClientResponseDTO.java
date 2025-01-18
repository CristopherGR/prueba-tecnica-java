package com.exercise.client_service.service.dtos;

import com.exercise.client_service.domain.enums.PersonGender;

public record ClientResponseDTO(
        Long personId,
        String name,
        PersonGender personGender,
        Long age,
        String identification,
        String address,
        String phone,
        String clientId,
        String password,
        boolean status
) {
}
