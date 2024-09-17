package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AssessmentDetailsResponseDTO;
import com.rafaelandrade.backend.dto.AssessmentResponseDTO;
import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.dto.AssessmentCreateDTO;
import com.rafaelandrade.backend.dto.AssessmentUpdateDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
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
import java.util.Map;
import java.util.Optional;

@Service
public class AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class);

    private final AssessmentRepository assessmentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final LegalEntityRepository legalEntityRepository;

    public AssessmentService(AssessmentRepository assessmentRepository, ItemRepository itemRepository, UserRepository userRepository, LegalEntityRepository legalEntityRepository) {
        this.assessmentRepository = assessmentRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.legalEntityRepository = legalEntityRepository;
    }

    @Transactional(readOnly = true)
    public Page<AssessmentDetailsResponseDTO> findAll(Pageable pageable) {
        logger.info("Fetching all assessments with pagination: {}", pageable);
        Page<Assessment> assessments = assessmentRepository.findAll(pageable);
        return assessments.map(assessment -> new AssessmentDetailsResponseDTO(assessment));
    }

    @Transactional(readOnly = true)
    public AssessmentDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Fetching assessment by id: {}", id);
        Optional<Assessment> assessmentObj = assessmentRepository.findById(id);
        Assessment assessmentEntity = assessmentObj.orElseThrow(() -> {
            logger.error("Assessment with id {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AssessmentDetailsResponseDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDetailsResponseDTO insert(AssessmentCreateDTO assessmentCreateDTO) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        logger.info("Inserting new assessment: {}", assessmentCreateDTO);
        Assessment assessmentEntity = new Assessment();
        checksIfAssociatedEntitiesExist(assessmentCreateDTO);
        checkUserHasConsumedItemBeforeReview(assessmentCreateDTO);
        copyDtoToEntity(assessmentCreateDTO, assessmentEntity);
        assessmentEntity.setCreatedAt(Instant.now());
        assessmentEntity.getUpdateHistory().put(Instant.now(), "N/A");
        assessmentEntity = assessmentRepository.save(assessmentEntity);
        logger.info("Assessment inserted successfully with id: {}", assessmentEntity.getId());
        return new AssessmentDetailsResponseDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDetailsResponseDTO update(Long id, AssessmentUpdateDTO assessmentDTO) throws ResourceNotFoundException {
        logger.info("Updating assessment with id: {}", id);
        try {
            Assessment assessmentEntity = assessmentRepository.getReferenceById(id);
            assessmentEntity.setUpdatedAt(Instant.now());

            Instant foundKey = null;
            for (Map.Entry<Instant, String> entry : assessmentEntity.getUpdateHistory().entrySet()) {
                if ("N/A".equals(entry.getValue())) {
                    foundKey = entry.getKey();
                    break;
                }
            }

            assessmentEntity.getUpdateHistory().put(foundKey, assessmentEntity.getComment());
            assessmentEntity.getUpdateHistory().put(Instant.now(), "N/A");
            assessmentEntity.setComment(assessmentDTO.getComment());
            assessmentEntity = assessmentRepository.save(assessmentEntity);
            logger.info("Assessment with id {} updated successfully", id);
            return new AssessmentDetailsResponseDTO(assessmentEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Assessment with id {} not found for update", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting assessment with id: {}", id);
        if (!assessmentRepository.existsById(id)) {
            logger.error("Assessment with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            assessmentRepository.deleteById(id);
            logger.info("Assessment with id {} deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting assessment with id: {}", id);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AssessmentCreateDTO assessmentCreateDTO, Assessment assessmentEntity) {
        logger.debug("Copying DTO to entity: {}", assessmentCreateDTO);
        assessmentEntity.setId(assessmentCreateDTO.getId());
        assessmentEntity.setComment(assessmentCreateDTO.getComment());
        assessmentEntity.setCreatedAt(assessmentCreateDTO.getCreatedAt());
        assessmentEntity.setUpdatedAt(assessmentCreateDTO.getUpdatedAt());
        assessmentEntity.setPoints(assessmentCreateDTO.getPoints());
        assessmentEntity.setUser(userRepository.getReferenceById(assessmentCreateDTO.getUser().getId()));
        assessmentEntity.setItem(itemRepository.getReferenceById(assessmentCreateDTO.getItem().getId()));
        assessmentEntity.setLegalEntity(legalEntityRepository.getReferenceById(assessmentCreateDTO.getLegalEntity().getId()));
    }

    private void checksIfAssociatedEntitiesExist(AssessmentCreateDTO assessmentCreateDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for: {}", assessmentCreateDTO);
        Long userId = assessmentCreateDTO.getUser().getId();
        Item item = itemRepository.getReferenceById(assessmentCreateDTO.getItem().getId());
        Long legalEntityId = item.getMenu().getLegalEntity().getId();

        if (!userRepository.existsById(userId)) {
            logger.error("User with id {} not found", userId);
            throw new ResourceNotFoundException("User with id " + userId + " not found.");
        }

        if (!itemRepository.existsById(item.getId())) {
            logger.error("Item with id {} not found", item.getId());
            throw new ResourceNotFoundException("Item with id " + item.getId() + " not found.");
        }

        if (!legalEntityRepository.existsById(legalEntityId)) {
            logger.error("LegalEntity with id {} not found", legalEntityId);
            throw new ResourceNotFoundException("LegalEntity with id " + legalEntityId + " not found.");
        }
    }

    private void checkUserHasConsumedItemBeforeReview(AssessmentCreateDTO assessmentCreateDTO) throws InvalidInputException {
        logger.debug("Checking if user has consumed item before review: {}", assessmentCreateDTO);
        User user = userRepository.getReferenceById(assessmentCreateDTO.getUser().getId());
        Assessment assessment = assessmentRepository.getReferenceById(assessmentCreateDTO.getId());

        for (Order order : user.getOrderHitory()) {
            if (order.getItems().contains(assessment.getItem())) {
                if (order.getOrderStatus() != OrderStatus.ORDER_DELIVERED) {
                    logger.error("The item with id {} has not been consumed by the user and cannot be evaluated", assessment.getItem().getId());
                    throw new InvalidInputException("The item has not been consumed by the user and cannot be evaluated.");
                }
            }
        }
    }
}