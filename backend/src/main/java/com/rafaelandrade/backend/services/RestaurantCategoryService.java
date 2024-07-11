package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.entities.RestaurantCategory;
import com.rafaelandrade.backend.repositories.RestaurantCategoryRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantCategoryDTO> findAll(Pageable pageable) {
        Page<RestaurantCategory> categories = restaurantCategoryRepository.findAll(pageable);
        return categories.map(category -> new RestaurantCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public RestaurantCategoryDTO findById(Long id) throws ResourceNotFoundException {
        Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findById(id);
        RestaurantCategory restaurantCategory = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RestaurantCategoryDTO(restaurantCategory);
    }

    @Transactional(readOnly = true)
    public RestaurantCategoryDTO findByName(String name) throws ResourceNotFoundException {
        Optional<RestaurantCategory> categoryObj = restaurantCategoryRepository.findByName(name);
        RestaurantCategory restaurantCategoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new RestaurantCategoryDTO(restaurantCategoryEntity);
    }

    @Transactional
    public RestaurantCategoryDTO insert(RestaurantCategoryDTO restaurantCategoryDTO) {
        RestaurantCategory restaurantCategoryEntity = new RestaurantCategory();
        restaurantCategoryEntity.setName(restaurantCategoryDTO.getName());
        restaurantCategoryEntity = restaurantCategoryRepository.save(restaurantCategoryEntity);
        return new RestaurantCategoryDTO(restaurantCategoryEntity);
    }

    @Transactional
    public RestaurantCategoryDTO update(Long id, RestaurantCategoryDTO restaurantCategoryDTO) throws ResourceNotFoundException{
        try {
            RestaurantCategory restaurantCategoryEntity = restaurantCategoryRepository.getReferenceById(id);
            restaurantCategoryEntity.setName(restaurantCategoryDTO.getName());
            restaurantCategoryEntity = restaurantCategoryRepository.save(restaurantCategoryEntity);
            return new RestaurantCategoryDTO(restaurantCategoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {

        if(!restaurantCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            restaurantCategoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }
}
