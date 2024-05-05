package com.danalves.agendapet.repository;

import com.danalves.agendapet.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findAllByActiveTrue(Pageable pageable);
}
