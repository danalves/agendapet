package com.danalves.agendapet.dto;

import com.danalves.agendapet.model.Animal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AnimalReceived(
        Long id,
        String name,
        String species,
        String breed,
        String gender,
        String dateOfBirth,
        boolean active,
        String action
){}
