package com.rafaelandrade.backend.dto;

import com.rafaelandrade.backend.entities.Assessment;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class AssessmentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer points;
    private UserDTO user;
    private LegalEntityDTO legalEntity;
    private ItemDTO item;
    private Map<Instant, String> updateHistory = new HashMap<>();

    public AssessmentDTO() {
    }

    public AssessmentDTO(Long id, String comment, Integer points, UserDTO user, ItemDTO item) {
        this.id = id;
        this.comment = comment;
        this.points = points;
        this.user = user;
        this.item = item;
    }

    public AssessmentDTO(Assessment assessmentEntity) {
        this.id = assessmentEntity.getId();
        this.comment = assessmentEntity.getComment();
        this.createdAt = assessmentEntity.getCreatedAt();
        this.updatedAt = assessmentEntity.getUpdatedAt();
        this.points = assessmentEntity.getPoints();
        this.user = new UserDTO(assessmentEntity.getUser());
        this.legalEntity = new LegalEntityDTO(assessmentEntity.getLegalEntity()); // Alterado para LegalEntityDTO
        this.updateHistory = (assessmentEntity.getUpdateHistory() != null) ? new HashMap<>(assessmentEntity.getUpdateHistory()) : new HashMap<>();
        this.item = (assessmentEntity.getItem() != null) ? new ItemDTO(assessmentEntity.getItem()) : null;  // Alterado para ItemDTO
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LegalEntityDTO getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityDTO legalEntity) {
        this.legalEntity = legalEntity;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public Map<Instant, String> getUpdateHistory() {
        return updateHistory;
    }

    public void setUpdateHistory(Map<Instant, String> updateHistory) {
        this.updateHistory = updateHistory;
    }
}
