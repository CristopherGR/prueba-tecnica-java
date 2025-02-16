package com.exercise.client_service.service.dtos;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.validation.constraints.*;

public record ClientCreateDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String name,

        @NotNull(message = "El género es obligatorio")
        PersonGender personGender,

        @NotNull(message = "La edad no puede ser nula")
        @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
        Long age,

        @NotBlank(message = "La identificación es obligatoria")
        @Size(min = 10, max = 20, message = "La identificación debe tener entre 10 y 20 caracteres")
        String identification,

        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
        String address,

        @NotBlank(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos numéricos")
        String phone,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        boolean status
) {
}
