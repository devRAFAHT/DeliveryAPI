package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.CategoryDTO;
import com.rafaelandrade.backend.entities.Category;
import com.rafaelandrade.backend.repositories.CategoryRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(category -> new CategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Category> categoryObj = categoryRepository.findById(id);
        Category category = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(category);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findByName(String name) throws ResourceNotFoundException {
        Optional<Category> categoryObj = categoryRepository.findByName(name);
        Category categoryEntity = categoryObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(categoryEntity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category categoryEntity = new Category();
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity = categoryRepository.save(categoryEntity);
        return new CategoryDTO(categoryEntity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) throws ResourceNotFoundException{
        try {
            Category categoryEntity = categoryRepository.getReferenceById(id);
            categoryEntity.setName(categoryDTO.getName());
            categoryEntity = categoryRepository.save(categoryEntity);
            return new CategoryDTO(categoryEntity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }


    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {

        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            categoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }
}
