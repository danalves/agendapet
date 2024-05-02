package com.danalves.agendapet.controller;

import com.danalves.agendapet.dto.NewAnimalForm;
import com.danalves.agendapet.model.Animal;
import com.danalves.agendapet.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @GetMapping("/list")
    public ResponseEntity<Page<Animal>> listAnimals(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listAnimals(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAnimal(@RequestBody @Valid NewAnimalForm form) {
        service.addAnimal(form);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


