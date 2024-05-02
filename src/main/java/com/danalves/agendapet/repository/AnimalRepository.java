package com.danalves.agendapet.repository;

import com.danalves.agendapet.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
