package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.DishVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishVariationRepository extends JpaRepository<DishVariation, Long> {
}
