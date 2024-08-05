package com.rafaelandrade.backend.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "tb_assessment")
public class Assessment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "legal_entity_id")
    private LegalEntity legalEntity;

    @ManyToOne()
    @JoinColumn(name = "item_id", nullable = true)
    private Item item;

    @ElementCollection
    @CollectionTable(name = "tb_assessment_update_history", joinColumns = @JoinColumn(name = "assessment_id"))
    @MapKeyColumn(name = "update_date")
    @Column(name = "comment")
    private Map<Instant, String> updateHistory = new HashMap<>();

    @OneToMany(mappedBy = "assessment")
    private Set<AssessmentResponse> assessmentResponses = new HashSet<>();

    public Assessment(){
    }

    public Assessment(Long id, String comment, Instant createdAt, Instant updatedAt, Integer points, User user, LegalEntity legalEntity, Item item) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.points = points;
        this.user = user;
        this.legalEntity = legalEntity;
        this.item = item;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LegalEntity getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        this.legalEntity = legalEntity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAssessmentResponses(Set<AssessmentResponse> assessmentResponses) {
        this.assessmentResponses = assessmentResponses;
    }

    public Map<Instant, String> getUpdateHistory() {
        return updateHistory;
    }

    public void setUpdateHistory(Map<Instant, String> updateHistory) {
        this.updateHistory = updateHistory;
    }

    public Set<AssessmentResponse> getAssessmentResponses() {
        return assessmentResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assessment that = (Assessment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
