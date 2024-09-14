package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.AssessmentResponse;

import java.time.Instant;

public class AssessmentResponseDTO {

    private Long id;
    private String comment;
    private Instant createdAt;
    private LegalEntityDetailsResponseDTO legalEntity;
    private AssessmentCreateDTO assessment;

    public AssessmentResponseDTO(){
    }

    public AssessmentResponseDTO(Long id, String comment, Instant createdAt, LegalEntityDetailsResponseDTO legalEntity, AssessmentCreateDTO assessment) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.legalEntity = legalEntity;
        this.assessment = assessment;
    }

    public AssessmentResponseDTO(AssessmentResponse assessmentResponseEntity) {
        this.id = assessmentResponseEntity.getId();
        this.comment = assessmentResponseEntity.getComment();
        this.createdAt = assessmentResponseEntity.getCreatedAt();
        this.legalEntity = new LegalEntityDetailsResponseDTO(assessmentResponseEntity.getLegalEntity());
        this.assessment = new AssessmentCreateDTO(assessmentResponseEntity.getAssessment());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LegalEntityDetailsResponseDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDetailsResponseDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public AssessmentCreateDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentCreateDTO assessment) {
        this.assessment = assessment;
    }
}