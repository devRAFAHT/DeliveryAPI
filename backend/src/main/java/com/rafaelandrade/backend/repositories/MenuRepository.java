package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByCategory(String category);
}
