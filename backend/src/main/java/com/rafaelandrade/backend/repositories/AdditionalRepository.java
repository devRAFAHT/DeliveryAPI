package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Additional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalRepository extends JpaRepository<Additional, Long> {
    Optional<Additional> findByName(String name);
}
