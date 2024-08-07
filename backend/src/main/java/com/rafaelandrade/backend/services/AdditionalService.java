package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.entities.Additional;
import com.rafaelandrade.backend.entities.AdditionalCategory;
import com.rafaelandrade.backend.repositories.AdditionalCategoryRepository;
import com.rafaelandrade.backend.repositories.AdditionalRepository;
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
public class AdditionalService {

    private final AdditionalRepository additionalRepository;

    private final AdditionalCategoryRepository additionalCategoryRepository;

    public AdditionalService(AdditionalRepository additionalRepository, AdditionalCategoryRepository additionalCategoryRepository) {
        this.additionalRepository = additionalRepository;
        this.additionalCategoryRepository = additionalCategoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<AdditionalDTO> findAll(Pageable pageable) {
        Page<Additional> additionalList = additionalRepository.findAll(pageable);
        return additionalList.map(additional -> new AdditionalDTO(additional));
    }

    @Transactional(readOnly = true)
    public AdditionalDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Additional> additionalObj = additionalRepository.findByName(name);
        Additional additionalEntity = additionalObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional(readOnly = true)
    public AdditionalDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Additional> additionalObj = additionalRepository.findById(id);
        Additional additionalEntity = additionalObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional
    public AdditionalDTO insert(AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        Additional additionalEntity = new Additional();
        copyDtoToEntity(additionalDTO, additionalEntity);
        additionalEntity = additionalRepository.save(additionalEntity);
        return new AdditionalDTO(additionalEntity);
    }

    @Transactional
    public AdditionalDTO update(Long id, AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        try {
            Additional additionalEntity = additionalRepository.getReferenceById(id);
            copyDtoToEntity(additionalDTO, additionalEntity);
            additionalEntity = additionalRepository.save(additionalEntity);
            return new AdditionalDTO(additionalEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!additionalRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            additionalRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(AdditionalDTO additionalDTO, Additional additionalEntity) throws ResourceNotFoundException {
        additionalEntity.setName(additionalDTO.getName());
        additionalEntity.setDescription(additionalDTO.getDescription());
        additionalEntity.setImgUrl(additionalDTO.getImgUrl());

        Optional<AdditionalCategory> categoryObj = additionalCategoryRepository.findById(additionalDTO.getCategory().getId());
        categoryObj.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        additionalEntity.setCategory(new AdditionalCategory(additionalDTO.getCategory()));
        additionalEntity.setPrice(additionalDTO.getPrice());
        additionalEntity.setSaleStatus(additionalDTO.getSaleStatus());
    }
}