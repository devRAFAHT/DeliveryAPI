package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.entities.RestaurantCategory;
import com.rafaelandrade.backend.repositories.RestaurantCategoryRepository;
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
public class RestaurantCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantCategoryService.class);

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantCategoryDTO> findAll(Pageable pageable) {
        logger.info("Request to find all restaurant categories with pagination: {}", pageable);
        Page<RestaurantCategory> categories = restaurantCategoryRepository.findAll(pageable);
        logger.info("Found {} restaurant categories", categories.getTotalElements());
        return categories.map(category -> {
            logger.debug("Mapping category id {} to DTO", category.getId());
            return new RestaurantCategoryDTO(category);
        });
    }

    @Transactional(readOnly = true)
    public RestaurantCategoryDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Request to find restaurant category by id: {}", id);
        Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findById(id);
        RestaurantCategory restaurantCategory = categoryObj.orElseThrow(() -> {
            logger.error("Restaurant category not found with id: {}", id);
            return new ResourceNotFoundException("Entity not found");
        });
        logger.info("Restaurant category found: {}", restaurantCategory);
        return new RestaurantCategoryDTO(restaurantCategory);
    }

    @Transactional(readOnly = true)
    public RestaurantCategoryDTO findByName(String name) throws ResourceNotFoundException {
        logger.info("Request to find restaurant category by name: {}", name);
        Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findByName(name);
        RestaurantCategory restaurantCategoryEntity = categoryObj.orElseThrow(() -> {
            logger.error("Restaurant category not found with name: {}", name);
            return new ResourceNotFoundException("Entity not found");
        });
        logger.info("Restaurant category found: {}", restaurantCategoryEntity);
        return new RestaurantCategoryDTO(restaurantCategoryEntity);
    }

    @Transactional
    public RestaurantCategoryDTO insert(RestaurantCategoryDTO restaurantCategoryDTO) {
        logger.info("Request to insert new restaurant category: {}", restaurantCategoryDTO);
        RestaurantCategory restaurantCategoryEntity = new RestaurantCategory();
        restaurantCategoryEntity.setName(restaurantCategoryDTO.getName());
        restaurantCategoryEntity = restaurantCategoryRepository.save(restaurantCategoryEntity);
        logger.info("Restaurant category inserted with id: {}", restaurantCategoryEntity.getId());
        return new RestaurantCategoryDTO(restaurantCategoryEntity);
    }

    @Transactional
    public RestaurantCategoryDTO update(Long id, RestaurantCategoryDTO restaurantCategoryDTO) throws ResourceNotFoundException {
        logger.info("Request to update restaurant category with id {}: {}", id, restaurantCategoryDTO);
        try {
            RestaurantCategory restaurantCategoryEntity = restaurantCategoryRepository.getReferenceById(id);
            restaurantCategoryEntity.setName(restaurantCategoryDTO.getName());
            restaurantCategoryEntity = restaurantCategoryRepository.save(restaurantCategoryEntity);
            logger.info("Restaurant category updated with id: {}", restaurantCategoryEntity.getId());
            return new RestaurantCategoryDTO(restaurantCategoryEntity);
        } catch (EntityNotFoundException e) {
            logger.error("Error updating restaurant category with id {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Request to delete restaurant category with id: {}", id);
        if (!restaurantCategoryRepository.existsById(id)) {
            logger.error("Restaurant category with id {} not found for deletion", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            restaurantCategoryRepository.deleteById(id);
            logger.info("Restaurant category deleted with id: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error deleting restaurant category with id {}: {}", id, e.getMessage());
            throw new DatabaseException("Integrity violation");
        }
    }
}