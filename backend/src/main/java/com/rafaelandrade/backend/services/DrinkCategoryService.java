package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DrinkCategoryDTO;
import com.rafaelandrade.backend.entities.DrinkCategory;
import com.rafaelandrade.backend.repositories.DrinkCategoryRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DrinkCategoryService {

    private final DrinkCategoryRepository drinkCategoryRepository;

    public DrinkCategoryService(DrinkCategoryRepository drinkCategoryRepository) {
        this.drinkCategoryRepository = drinkCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<DrinkCategoryDTO> findAll(Pageable pageable) {
        Page<DrinkCategory> categories = drinkCategoryRepository.findAll(pageable);
        return categories.map(category -> new DrinkCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public DrinkCategoryDTO findById(Long id) throws ResourceNotFoundException {
        Optional<DrinkCategory> categoryObj = drinkCategoryRepository.findById(id);
        DrinkCategory drinkCategory = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DrinkCategoryDTO(drinkCategory);
    }

    @Transactional(readOnly = true)
    public DrinkCategoryDTO findByName(String name) throws ResourceNotFoundException {
        Optional<DrinkCategory> categoryObj = drinkCategoryRepository.findByName(name);
        DrinkCategory drinkCategoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new DrinkCategoryDTO(drinkCategoryEntity);
    }

    @Transactional
    public DrinkCategoryDTO insert(DrinkCategoryDTO DrinkCategoryDTO) {
        DrinkCategory drinkCategoryEntity = new DrinkCategory();
        drinkCategoryEntity.setName(DrinkCategoryDTO.getName());
        drinkCategoryEntity = drinkCategoryRepository.save(drinkCategoryEntity);
        return new DrinkCategoryDTO(drinkCategoryEntity);
    }

    @Transactional
    public DrinkCategoryDTO update(Long id, DrinkCategoryDTO DrinkCategoryDTO) throws ResourceNotFoundException{
        try {
            DrinkCategory drinkCategoryEntity = drinkCategoryRepository.getReferenceById(id);
            drinkCategoryEntity.setName(DrinkCategoryDTO.getName());
            drinkCategoryEntity = drinkCategoryRepository.save(drinkCategoryEntity);
            return new DrinkCategoryDTO(drinkCategoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {

        if(!drinkCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            drinkCategoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }
}
