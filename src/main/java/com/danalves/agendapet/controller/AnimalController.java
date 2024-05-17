package com.danalves.agendapet.controller;

import com.danalves.agendapet.dto.NewAnimalRequest;
import com.danalves.agendapet.model.Animal;
import com.danalves.agendapet.service.AnimalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class AnimalController {

    private final AnimalService service;

    @GetMapping("/list")
    public ResponseEntity<Page<Animal>> listAnimals(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAnimals(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAnimal(@RequestBody @Valid NewAnimalRequest form) {
        service.addAnimal(form);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


