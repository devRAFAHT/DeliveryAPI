package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, Long> {

    Optional<DishCategory> findByName(String name);
    DishCategory getCategoryByName(String name);

}
