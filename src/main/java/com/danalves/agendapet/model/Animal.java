package com.danalves.agendapet.model;

import com.danalves.agendapet.dto.NewAnimalRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(nullable = false)
    private boolean active;

    public Animal(NewAnimalRequest form) {
        this.name = form.name();
        this.species = form.species();
        this.breed = form.breed();
        this.gender = form.gender();
        this.dateOfBirth = form.dateOfBirth();
        this.active = true;
    }

}
