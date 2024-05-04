package com.danalves.agendapet.dto;

import jakarta.validation.constraints.NotBlank;

public record NewAnimalRequest(
        @NotBlank(message = "Empty fields will not be accepted.")
        String name,
        @NotBlank(message = "Empty fields will not be accepted.")
        String species,
        @NotBlank(message = "Empty fields will not be accepted.")
        String breed,
        @NotBlank(message = "Empty fields will not be accepted.")
        String gender,
        @NotBlank(message = "Empty fields will not be accepted.")
        String dateOfBirth
) {
}
