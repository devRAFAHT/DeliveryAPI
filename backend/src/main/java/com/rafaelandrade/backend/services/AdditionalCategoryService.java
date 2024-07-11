package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AdditionalCategoryDTO;
import com.rafaelandrade.backend.entities.AdditionalCategory;
import com.rafaelandrade.backend.repositories.AdditionalCategoryRepository;
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
public class AdditionalCategoryService {

    private final AdditionalCategoryRepository additionalCategoryRepository;

    public AdditionalCategoryService(AdditionalCategoryRepository additionalCategoryRepository) {
        this.additionalCategoryRepository = additionalCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<AdditionalCategoryDTO> findAll(Pageable pageable) {
        Page<AdditionalCategory> categories = additionalCategoryRepository.findAll(pageable);
        return categories.map(category -> new AdditionalCategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public AdditionalCategoryDTO findById(Long id) throws ResourceNotFoundException {
        Optional<AdditionalCategory> categoryObj = additionalCategoryRepository.findById(id);
        AdditionalCategory additionalCategory = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AdditionalCategoryDTO(additionalCategory);
    }

    @Transactional(readOnly = true)
    public AdditionalCategoryDTO findByName(String name) throws ResourceNotFoundException {
        Optional<AdditionalCategory> categoryObj = additionalCategoryRepository.findByName(name);
        AdditionalCategory additionalCategoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AdditionalCategoryDTO(additionalCategoryEntity);
    }

    @Transactional
    public AdditionalCategoryDTO insert(AdditionalCategoryDTO additionalCategoryDTO) {
        AdditionalCategory additionalCategoryEntity = new AdditionalCategory();
        additionalCategoryEntity.setName(additionalCategoryDTO.getName());
        additionalCategoryEntity = additionalCategoryRepository.save(additionalCategoryEntity);
        return new AdditionalCategoryDTO(additionalCategoryEntity);
    }

    @Transactional
    public AdditionalCategoryDTO update(Long id, AdditionalCategoryDTO additionalCategoryDTO) throws ResourceNotFoundException{
        try {
            AdditionalCategory additionalCategoryEntity = additionalCategoryRepository.getReferenceById(id);
            additionalCategoryEntity.setName(additionalCategoryDTO.getName());
            additionalCategoryEntity = additionalCategoryRepository.save(additionalCategoryEntity);
            return new AdditionalCategoryDTO(additionalCategoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {

        if(!additionalCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            additionalCategoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }
}
