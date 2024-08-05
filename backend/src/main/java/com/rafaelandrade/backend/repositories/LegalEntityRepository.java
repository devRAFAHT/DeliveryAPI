package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {

}
