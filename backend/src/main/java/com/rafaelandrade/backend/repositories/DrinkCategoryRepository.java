package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Long> {
    Optional<DrinkCategory> findByName(String name);
    DrinkCategory getCategoryByName(String name);
}
