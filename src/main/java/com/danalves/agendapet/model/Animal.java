package com.danalves.agendapet.model;

import com.danalves.agendapet.dto.NewAnimalRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;


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

    public String toString(String action){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("species", species);
        json.put("breed", breed);
        json.put("gender", gender);
        json.put("dateOfBirth", dateOfBirth);
        json.put("active", active);
        json.put("action", action);

        return json.toString();
    }

}
