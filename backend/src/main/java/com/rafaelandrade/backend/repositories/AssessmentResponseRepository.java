package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.AssessmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentResponseRepository extends JpaRepository<AssessmentResponse, Long> {
}
