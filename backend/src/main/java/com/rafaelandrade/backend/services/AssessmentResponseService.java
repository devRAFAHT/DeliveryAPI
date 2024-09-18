package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AssessmentResponseDTO;
import com.rafaelandrade.backend.dto.AssessmentResponseUpdateDTO;
import com.rafaelandrade.backend.entities.Assessment;
import com.rafaelandrade.backend.entities.AssessmentResponse;
import com.rafaelandrade.backend.entities.LegalEntity;
import com.rafaelandrade.backend.entities.Menu;
import com.rafaelandrade.backend.repositories.AssessmentRepository;
import com.rafaelandrade.backend.repositories.AssessmentResponseRepository;
import com.rafaelandrade.backend.repositories.LegalEntityRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class AssessmentResponseService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentResponseService.class);

    private final AssessmentResponseRepository assessmentResponseRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final AssessmentRepository assessmentRepository;

    public AssessmentResponseService(AssessmentResponseRepository assessmentResponseRepository, LegalEntityRepository legalEntityRepository, AssessmentRepository assessmentRepository) {
        this.assessmentResponseRepository = assessmentResponseRepository;
        this.legalEntityRepository = legalEntityRepository;
        this.assessmentRepository = assessmentRepository;
    }

    @Transactional(readOnly = true)
    public Page<AssessmentResponseDTO> findAll(Pageable pageable) {
        logger.info("Finding all assessment responses with pageable: {}", pageable);
        Page<AssessmentResponse> assessmentsResponses = assessmentResponseRepository.findAll(pageable);
        logger.info("Found {} assessment responses", assessmentsResponses.getTotalElements());
        return assessmentsResponses.map(AssessmentResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public AssessmentResponseDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding assessment response by id: {}", id);
        Optional<AssessmentResponse> assessmentResponseObj = assessmentResponseRepository.findById(id);
        if (assessmentResponseObj.isEmpty()) {
            logger.error("Assessment response with id {} not found", id);
            throw new ResourceNotFoundException("Entity not found");
        }
        logger.info("Found assessment response: {}", assessmentResponseObj.get());
        return new AssessmentResponseDTO(assessmentResponseObj.get());
    }

    @Transactional
    public AssessmentResponseDTO insert(AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        logger.info("Inserting new assessment response: {}", assessmentResponseDTO);
        AssessmentResponse assessmentResponseEntity = new AssessmentResponse();
        checksIfAssociatedEntitiesExist(assessmentResponseDTO);
        validateItemBelongsToLegalEntity(assessmentResponseDTO);
        copyDtoToEntity(assessmentResponseDTO, assessmentResponseEntity);
        assessmentResponseEntity.setCreatedAt(Instant.now());
        assessmentResponseEntity = assessmentResponseRepository.save(assessmentResponseEntity);
        logger.info("Inserted assessment response: {}", assessmentResponseEntity);
        return new AssessmentResponseDTO(assessmentResponseEntity);
    }

    @Transactional
    public AssessmentResponseDTO update(Long id, AssessmentResponseUpdateDTO assessmentResponseDTO) throws ResourceNotFoundException, InvalidInputException {
        logger.info("Updating assessment response with id: {}", id);
        try {
            AssessmentResponse assessmentResponseEntity = assessmentResponseRepository.getReferenceById(id);
            assessmentResponseEntity.setComment(assessmentResponseDTO.getComment());
            assessmentResponseEntity = assessmentResponseRepository.save(assessmentResponseEntity);
            logger.info("Updated assessment response: {}", assessmentResponseEntity);
            return new AssessmentResponseDTO(assessmentResponseEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Id not found: {}", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting assessment response with id: {}", id);
        if (!assessmentResponseRepository.existsById(id)) {
            logger.error("Id not found: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            assessmentResponseRepository.deleteById(id);
            logger.info("Deleted assessment response with id: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AssessmentResponseDTO assessmentResponseDTO, AssessmentResponse assessmentResponseEntity) throws ResourceNotFoundException, InvalidInputException {
        logger.debug("Copying DTO to entity: {}", assessmentResponseDTO);
        assessmentResponseEntity.setComment(assessmentResponseDTO.getComment());
        assessmentResponseEntity.setAssessment(assessmentRepository.getReferenceById(assessmentResponseDTO.getAssessment().getId()));
        LegalEntity legalEntity = legalEntityRepository.getReferenceById(assessmentResponseDTO.getLegalEntity().getId());
        assessmentResponseEntity.setLegalEntity(legalEntity);
    }

    private void checksIfAssociatedEntitiesExist(AssessmentResponseDTO assessmentResponseDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for DTO: {}", assessmentResponseDTO);
        Long assessmentId = assessmentResponseDTO.getAssessment().getId();
        Long legalEntityId = assessmentResponseDTO.getLegalEntity().getId();

        if (!assessmentRepository.existsById(assessmentId)) {
            logger.error("Assessment with id {} not found.", assessmentId);
            throw new ResourceNotFoundException("Assessment with id " + assessmentId + " not found.");
        }

        if (!legalEntityRepository.existsById(legalEntityId)) {
            logger.error("Legal entity with id {} not found.", legalEntityId);
            throw new ResourceNotFoundException("Legal entity with id " + legalEntityId + " not found.");
        }
    }

    private void validateItemBelongsToLegalEntity(AssessmentResponseDTO assessmentResponseDTO) throws InvalidInputException {
        logger.debug("Validating item belongs to legal entity for DTO: {}", assessmentResponseDTO);
        LegalEntity legalEntity = legalEntityRepository.getReferenceById(assessmentResponseDTO.getLegalEntity().getId());
        Assessment assessment = assessmentRepository.getReferenceById(assessmentResponseDTO.getAssessment().getId());

        for (Menu menu : legalEntity.getMenus()) {
            if (!menu.getItems().contains(assessment.getItem())) {
                logger.error("A legal entity cannot respond to an evaluation of another legal entity. DTO: {}", assessmentResponseDTO);
                throw new InvalidInputException("A legal entity cannot respond to an evaluation of another legal entity.");
            }
        }
    }
}
