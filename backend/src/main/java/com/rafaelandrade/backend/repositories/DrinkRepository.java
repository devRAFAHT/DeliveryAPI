package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Optional<Drink> findByName(String name);
}
