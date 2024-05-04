package com.danalves.agendapet.model;

import com.danalves.agendapet.dto.NewAnimalRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species;
    @Column(nullable = false)
    private String breed;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String dateOfBirth;

    public Animal(NewAnimalRequest form) {
        this.name = form.name();
        this.species = form.species();
        this.breed = form.breed();
        this.gender = form.gender();
        this.dateOfBirth = form.dateOfBirth();
    }

}
