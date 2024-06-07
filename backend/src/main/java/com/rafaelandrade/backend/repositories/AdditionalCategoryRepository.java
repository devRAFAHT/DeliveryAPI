package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.AdditionalCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdditionalCategoryRepository extends JpaRepository<AdditionalCategory, Long> {
    Optional<AdditionalCategory> findByName(String name);
}
