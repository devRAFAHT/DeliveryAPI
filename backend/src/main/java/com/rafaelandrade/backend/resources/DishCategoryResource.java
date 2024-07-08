package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishCategoryDTO;
import com.rafaelandrade.backend.services.DishCategoryService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/dish-categories")
public class DishCategoryResource {

    private final DishCategoryService dishCategoryService;

    public DishCategoryResource(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @GetMapping
    public ResponseEntity<Page<DishCategoryDTO>> findAll(Pageable pageable){
        Page<DishCategoryDTO> categories = dishCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DishCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishCategoryDTO dishCategoryDTO = dishCategoryService.findById(id);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<DishCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException{
        DishCategoryDTO dishCategoryDTO = dishCategoryService.findByName(name);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @PostMapping
    public ResponseEntity<DishCategoryDTO> insert(@Valid @RequestBody DishCategoryDTO dishCategoryDTO) {
        dishCategoryDTO = dishCategoryService.insert(dishCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishCategoryDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DishCategoryDTO> update(@Valid @PathVariable Long id, @RequestBody DishCategoryDTO dishCategoryDTO) throws ResourceNotFoundException {
        dishCategoryDTO = dishCategoryService.update(id, dishCategoryDTO);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DishCategoryDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
