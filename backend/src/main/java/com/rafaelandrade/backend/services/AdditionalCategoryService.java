package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AdditionalCategoryDTO;
import com.rafaelandrade.backend.entities.AdditionalCategory;
import com.rafaelandrade.backend.repositories.AdditionalCategoryRepository;
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
public class AdditionalCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalCategoryService.class);

    private final AdditionalCategoryRepository additionalCategoryRepository;

    public AdditionalCategoryService(AdditionalCategoryRepository additionalCategoryRepository) {
        this.additionalCategoryRepository = additionalCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<AdditionalCategoryDTO> findAll(Pageable pageable) {
        logger.info("Fetching all categories with pagination: {}", pageable);
        Page<AdditionalCategory> categories = additionalCategoryRepository.findAll(pageable);
        return categories.map(category -> new AdditionalCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public AdditionalCategoryDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Fetching category by id: {}", id);
        Optional<AdditionalCategory> categoryObj = additionalCategoryRepository.findById(id);
        AdditionalCategory additionalCategory = categoryObj.orElseThrow(() -> {
            logger.error("Category with id {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AdditionalCategoryDTO(additionalCategory);
    }

    @Transactional(readOnly = true)
    public AdditionalCategoryDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Fetching category by name: {}", name);
        Optional<AdditionalCategory> categoryObj = additionalCategoryRepository.findByName(name);
        AdditionalCategory additionalCategoryEntity = categoryObj.orElseThrow(() -> {
            logger.error("Category with name {} not found", name);
            return new ResourceNotFoundException("Entity not found");
        });
        return new AdditionalCategoryDTO(additionalCategoryEntity);
    }

    @Transactional
    public AdditionalCategoryDTO insert(AdditionalCategoryDTO additionalCategoryDTO) {
        logger.info("Inserting new category: {}", additionalCategoryDTO.getName());
        AdditionalCategory additionalCategoryEntity = new AdditionalCategory();
        additionalCategoryEntity.setName(additionalCategoryDTO.getName());
        additionalCategoryEntity = additionalCategoryRepository.save(additionalCategoryEntity);
        logger.info("Category inserted successfully with id: {}", additionalCategoryEntity.getId());
        return new AdditionalCategoryDTO(additionalCategoryEntity);
    }

    @Transactional
    public AdditionalCategoryDTO update(Long id, AdditionalCategoryDTO additionalCategoryDTO) throws ResourceNotFoundException {
        try {
            logger.info("Updating category with id: {}", id);
            AdditionalCategory additionalCategoryEntity = additionalCategoryRepository.getReferenceById(id);
            additionalCategoryEntity.setName(additionalCategoryDTO.getName());
            additionalCategoryEntity = additionalCategoryRepository.save(additionalCategoryEntity);
            logger.info("Category with id {} updated successfully", id);
            return new AdditionalCategoryDTO(additionalCategoryEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Category with id {} not found for update", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting category with id: {}", id);
        if (!additionalCategoryRepository.existsById(id)) {
            logger.error("Category with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            additionalCategoryRepository.deleteById(id);
            logger.info("Category with id {} deleted successfully", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting category with id: {}", id);
            throw new DatabaseException("Integrity violation");
        }
    }
}
