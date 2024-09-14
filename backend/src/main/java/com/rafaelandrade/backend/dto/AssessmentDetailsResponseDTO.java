package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Assessment;

import java.time.Instant;

public class AssessmentDetailsResponseDTO {

    private Long id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer points;
    private UserDetailsResponseDTO user;
    private LegalEntityDetailsResponseDTO legalEntity;
    private ItemDTO item;

    public AssessmentDetailsResponseDTO(){
    }

    public AssessmentDetailsResponseDTO(Assessment assessmentEntity) {
        this.id = assessmentEntity.getId();
        this.comment = assessmentEntity.getComment();
        this.createdAt = assessmentEntity.getCreatedAt();
        this.updatedAt = assessmentEntity.getUpdatedAt();
        this.points = assessmentEntity.getPoints();
        this.user = new UserDetailsResponseDTO(assessmentEntity.getUser());
        this.legalEntity = new LegalEntityDetailsResponseDTO(assessmentEntity.getLegalEntity());
        this.item = new ItemDTO(assessmentEntity.getItem());
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public UserDetailsResponseDTO getUser() {
        return user;
    }

    public void setUser(UserDetailsResponseDTO user) {
        this.user = user;
    }

    public LegalEntityDetailsResponseDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDetailsResponseDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
