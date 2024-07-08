package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Additional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AdditionalRepository extends JpaRepository<Additional, Long> {
    Optional<Additional> findByName(String name);
}
