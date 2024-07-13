package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Bag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BagRepository extends JpaRepository<Bag, Long> {

}
