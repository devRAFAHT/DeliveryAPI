package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.DrinkCategory;
import com.rafaelandrade.backend.entities.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Long> {
    Optional<RestaurantCategory> findByName(String name);
    RestaurantCategory getCategoryByName(String name);
}
