package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByName(String name);
}
