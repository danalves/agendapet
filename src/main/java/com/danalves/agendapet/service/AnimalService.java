package com.danalves.agendapet.service;

import com.danalves.agendapet.dto.NewAnimalRequest;
import com.danalves.agendapet.model.Animal;
import com.danalves.agendapet.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public Page<Animal> listAnimals(Pageable pageable) {
        return animalRepository.findAllByActiveTrue(pageable);
    }

    public void addAnimal(NewAnimalRequest form) {
        var newAnimal = new Animal(form);
        animalRepository.save(newAnimal);
    }
}
