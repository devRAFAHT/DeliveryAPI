package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishCategoryDTO;
import com.rafaelandrade.backend.entities.DishCategory;
import com.rafaelandrade.backend.repositories.DishCategoryRepository;
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
public class DishCategoryService {

    private final DishCategoryRepository dishCategoryRepository;

    public DishCategoryService(DishCategoryRepository dishCategoryRepository) {
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DishCategoryDTO> findAll(Pageable pageable) {
        Page<DishCategory> categories = dishCategoryRepository.findAll(pageable);
        return categories.map(category -> new DishCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public DishCategoryDTO findById(Long id) throws ResourceNotFoundException {
        Optional<DishCategory> categoryObj = dishCategoryRepository.findById(id);
        DishCategory dishCategory = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishCategoryDTO(dishCategory);
    }

    @Transactional(readOnly = true)
    public DishCategoryDTO findByName(String name) throws ResourceNotFoundException {
        Optional<DishCategory> categoryObj = dishCategoryRepository.findByName(name);
        DishCategory dishCategoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DishCategoryDTO(dishCategoryEntity);
    }

    @Transactional
    public DishCategoryDTO insert(DishCategoryDTO dishCategoryDTO) {
        DishCategory dishCategoryEntity = new DishCategory();
        dishCategoryEntity.setName(dishCategoryDTO.getName());
        dishCategoryEntity = dishCategoryRepository.save(dishCategoryEntity);
        return new DishCategoryDTO(dishCategoryEntity);
    }

    @Transactional
    public DishCategoryDTO update(Long id, DishCategoryDTO dishCategoryDTO) throws ResourceNotFoundException{
        try {
            DishCategory dishCategoryEntity = dishCategoryRepository.getReferenceById(id);
            dishCategoryEntity.setName(dishCategoryDTO.getName());
            dishCategoryEntity = dishCategoryRepository.save(dishCategoryEntity);
            return new DishCategoryDTO(dishCategoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {

        if(!dishCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            dishCategoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }
}
