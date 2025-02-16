package com.exercise.client_service.service.dtos;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.validation.constraints.*;

public record ClientUpdateDTO(
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String name,

        PersonGender personGender,

        @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
        Long age,

        @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
        String address,

        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos numéricos")
        String phone,

        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        Boolean status
) { }

