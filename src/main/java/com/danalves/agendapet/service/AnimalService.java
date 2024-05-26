package com.danalves.agendapet.service;

import com.danalves.agendapet.dto.NewAnimalRequest;
import com.danalves.agendapet.event.AnimalCreatedEvent;
import com.danalves.agendapet.exception.AnimalNotFoundException;
import com.danalves.agendapet.model.Animal;
import com.danalves.agendapet.repository.AnimalRepository;
import com.danalves.agendapet.service.aws.AwsSnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalService {

    private final AnimalRepository animalRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final AwsSnsService snsService;

    public Page<Animal> findAll(Pageable pageable) {
        return animalRepository.findAllByActiveTrue(pageable);
    }

    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public void addAnimal(NewAnimalRequest form) {
        var newAnimal = new Animal(form);
        animalRepository.save(newAnimal);
        eventPublisher.publishEvent(new AnimalCreatedEvent(newAnimal));
    }

    public void deleteAnimal(Long id) {
        findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal de id %s não encontrado!".formatted(id)));
        animalRepository.deleteById(id);
    }

    public Animal updateAnimal(Long id, NewAnimalRequest form) {
        var animal = findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal de id %s não encontrado!".formatted(id)));

        animal.setName(form.name());
        animal.setSpecies(form.species());
        animal.setBreed(form.breed());
        animal.setGender(form.gender());
        animal.setDateOfBirth(form.dateOfBirth());
        animalRepository.save(animal);

        return animal;
    }

    @EventListener
    public void onAnimalCreated(AnimalCreatedEvent event) {
        Animal animal = event.getAnimal();

        log.info("Animal {} criado! Disparando mensagem para fila AWS.", animal.getName());
        snsService.publish(animal.toString("I"));
    }
}
