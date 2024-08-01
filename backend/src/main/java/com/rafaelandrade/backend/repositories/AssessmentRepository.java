package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Assessment;
import com.rafaelandrade.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
