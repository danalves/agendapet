package com.danalves.agendapet.repository;

import com.danalves.agendapet.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando nenhum animal ativo esta cadastrado")
    void findAllByActiveTrueCase1() {
        //given ou arrange
        cadastrarAnimal("Emmy", "Gato", "SRD", "Fêmea", "01.01.2022", false);

        //when ou act
        var animais = animalRepository.findAllByActiveTrue(Pageable.ofSize(2));

        //then ou assert
        assertThat(animais.getTotalElements() == 0).isTrue();
    }

    @Test
    @DisplayName("Deveria devolver a lista de animais quando algum animal ativo esta cadastrado")
    void findAllByActiveTrueCase2() {
        //given ou arrange
        cadastrarAnimal("Emmy", "Gato", "SRD", "Fêmea", "01.01.2022", true);
        cadastrarAnimal("Theo", "Gato", "SRD", "Macho", "01.01.2022", true);

        //when ou act
        var animais = animalRepository.findAllByActiveTrue(Pageable.ofSize(2));

        //then ou assert
        assertThat(animais.getTotalElements() > 0).isTrue();
    }

    private void cadastrarAnimal(String name, String species, String breed, String gender, String dob, boolean active) {
        em.persist(new Animal(null, name, species, breed, gender, dob, active));
    }
}