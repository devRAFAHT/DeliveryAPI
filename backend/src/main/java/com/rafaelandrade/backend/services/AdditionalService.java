package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.entities.Additional;
import com.rafaelandrade.backend.entities.AdditionalCategory;
import com.rafaelandrade.backend.repositories.AdditionalCategoryRepository;
import com.rafaelandrade.backend.repositories.AdditionalRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdditionalService {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalService.class); // Logger adicionado

    private final AdditionalRepository additionalRepository;
    private final AdditionalCategoryRepository additionalCategoryRepository;

    public AdditionalService(AdditionalRepository additionalRepository, AdditionalCategoryRepository additionalCategoryRepository) {
        this.additionalRepository = additionalRepository;
        this.additionalCategoryRepository = additionalCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<AdditionalDTO> findAll(Pageable pageable) {
        logger.info("Fetching all additional items");
        Page<Additional> additionalList = additionalRepository.findAll(pageable);
        return additionalList.map(additional -> new AdditionalDTO(additional));
    }

    @Transactional(readOnly = true)
    public AdditionalDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding additional by name: {}", name);
        Optional<Additional> additionalObj = additionalRepository.findByName(name);
        Additional additionalEntity = additionalObj.orElseThrow(() -> {
            logger.error("Additional item with name {} not found", name);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional(readOnly = true)
    public AdditionalDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding additional by ID: {}", id);
        Optional<Additional> additionalObj = additionalRepository.findById(id);
        Additional additionalEntity = additionalObj.orElseThrow(() -> {
            logger.error("Additional item with ID {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional
    public AdditionalDTO insert(AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        logger.info("Inserting new additional item");
        Additional additionalEntity = new Additional();
        checksIfAssociatedEntitiesExist(additionalDTO);
        copyDtoToEntity(additionalDTO, additionalEntity);
        additionalEntity = additionalRepository.save(additionalEntity);
        logger.info("Additional item inserted with ID: {}", additionalEntity.getId());
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional
    public AdditionalDTO update(Long id, AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        logger.info("Updating additional item with ID: {}", id);
        try {
            Additional additionalEntity = additionalRepository.getReferenceById(id);
            checksIfAssociatedEntitiesExist(additionalDTO);
            copyDtoToEntity(additionalDTO, additionalEntity);
            additionalEntity = additionalRepository.save(additionalEntity);
            logger.info("Additional item with ID {} updated successfully", id);
            return new AdditionalDTO(additionalEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Additional item with ID {} not found for update", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting additional item with ID: {}", id);
        if (!additionalRepository.existsById(id)) {
            logger.error("Additional item with ID {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            additionalRepository.deleteById(id);
            logger.info("Additional item with ID {} deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting additional item with ID: {}", id);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AdditionalDTO additionalDTO, Additional additionalEntity) {
        logger.debug("Copying DTO to entity");
        additionalEntity.setName(additionalDTO.getName());
        additionalEntity.setDescription(additionalDTO.getDescription());
        additionalEntity.setImgUrl(additionalDTO.getImgUrl());
        additionalEntity.setCategory(new AdditionalCategory(additionalDTO.getCategory()));
        additionalEntity.setPrice(additionalDTO.getPrice());
        additionalEntity.setSaleStatus(additionalDTO.getSaleStatus());
    }

    private void checksIfAssociatedEntitiesExist(AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        logger.info("Checking if associated entities exist for category ID: {}", additionalDTO.getCategory().getId());
        if (!additionalCategoryRepository.existsById(additionalDTO.getCategory().getId())) {
            logger.error("Category with ID {} not found", additionalDTO.getCategory().getId());
            throw new ResourceNotFoundException("Category with id " + additionalDTO.getCategory().getId() + " not found");
        }
    }
}
