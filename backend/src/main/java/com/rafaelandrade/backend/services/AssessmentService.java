package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AssessmentDetailsResponseDTO;
import com.rafaelandrade.backend.entities.common.OrderStatus;
import com.rafaelandrade.backend.dto.AssessmentCreateDTO;
import com.rafaelandrade.backend.dto.AssessmentUpdateDTO;
import com.rafaelandrade.backend.entities.*;
import com.rafaelandrade.backend.repositories.*;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
        Page<Assessment> assessments = assessmentRepository.findAll(pageable);
        return assessments.map(assessment -> new AssessmentDetailsResponseDTO(assessment));
    }

    @Transactional(readOnly = true)
    public AssessmentDetailsResponseDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Assessment> assessmentObj = assessmentRepository.findById(id);
        Assessment assessmentEntity = assessmentObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AssessmentDetailsResponseDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDetailsResponseDTO insert(AssessmentCreateDTO assessmentCreateDTO) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        Assessment assessmentEntity = new Assessment();
        copyDtoToEntity(assessmentCreateDTO, assessmentEntity);
        assessmentEntity.setCreatedAt(Instant.now());
        assessmentEntity.getUpdateHistory().put(Instant.now(), "N/A");
        assessmentEntity = assessmentRepository.save(assessmentEntity);
        return new AssessmentDetailsResponseDTO(assessmentEntity);
    }

    @Transactional
    public AssessmentDetailsResponseDTO update(Long id, AssessmentUpdateDTO assessmentDTO) throws ResourceNotFoundException {
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
            return new AssessmentDetailsResponseDTO(assessmentEntity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!assessmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            assessmentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AssessmentCreateDTO assessmentCreateDTO, Assessment assessmentEntity) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        assessmentEntity.setId(assessmentCreateDTO.getId());
        assessmentEntity.setComment(assessmentCreateDTO.getComment());
        assessmentEntity.setCreatedAt(assessmentCreateDTO.getCreatedAt());
        assessmentEntity.setUpdatedAt(assessmentCreateDTO.getUpdatedAt());
        assessmentEntity.setPoints(assessmentCreateDTO.getPoints());

        User user = userRepository.findById(assessmentCreateDTO.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + assessmentCreateDTO.getUser().getId() + " not found."));
        assessmentEntity.setUser(user);

        if (assessmentCreateDTO.getItem() == null) {
            throw new InvalidInputException("Item not specified in DTO.");
        }

        Item item = itemRepository.findById(assessmentCreateDTO.getItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item with id " + assessmentCreateDTO.getItem().getId() + " not found."));
        assessmentEntity.setItem(item);

        LegalEntity legalEntity = legalEntityRepository.findById(item.getMenu().getLegalEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("LegalEntity with id " + item.getMenu().getLegalEntity().getId() + " not found."));
        assessmentEntity.setLegalEntity(legalEntity);

        for (Order order : user.getOrderHitory()) {
            if (!order.getItems().contains(assessmentEntity.getItem())) {
                if (order.getOrderStatus() != OrderStatus.ORDER_DELIVERED) {
                    throw new InvalidInputException("The item has not been consumed by the user and cannot be evaluated.");
                }
            }
        }
    }
}
