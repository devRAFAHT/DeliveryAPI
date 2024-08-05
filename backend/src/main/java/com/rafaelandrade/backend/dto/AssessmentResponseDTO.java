package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.AssessmentResponse;

import java.time.Instant;

public class AssessmentResponseDTO {

    private Long id;
    private String comment;
    private Instant createdAt;
    private LegalEntityDTO legalEntity;
    private AssessmentDTO assessment;

    public AssessmentResponseDTO(){
    }

    public AssessmentResponseDTO(Long id, String comment, LegalEntityDTO legalEntity, AssessmentDTO assessment) {
        this.id = id;
        this.comment = comment;
        this.legalEntity = legalEntity;
        this.assessment = assessment;
    }

    public AssessmentResponseDTO(AssessmentResponse assessmentResponseEntity) {
        this.id = assessmentResponseEntity.getId();
        this.comment = assessmentResponseEntity.getComment();
        this.createdAt = assessmentResponseEntity.getCreatedAt();
        this.legalEntity = new LegalEntityDTO(assessmentResponseEntity.getLegalEntity());
        this.assessment = new AssessmentDTO(assessmentResponseEntity.getAssessment());
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

    public LegalEntityDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
    }
}
