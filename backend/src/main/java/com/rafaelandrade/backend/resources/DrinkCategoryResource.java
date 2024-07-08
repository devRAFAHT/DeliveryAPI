package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DrinkCategoryDTO;
import com.rafaelandrade.backend.services.DrinkCategoryService;
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
@RequestMapping(value = "/drink-categories")
public class DrinkCategoryResource {

    private final DrinkCategoryService drinkCategoryService;

    public DrinkCategoryResource(DrinkCategoryService drinkCategoryService) {
        this.drinkCategoryService = drinkCategoryService;
    }

    @GetMapping
    public ResponseEntity<Page<DrinkCategoryDTO>> findAll(Pageable pageable){
        Page<DrinkCategoryDTO> categories = drinkCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DrinkCategoryDTO drinkCategoryDTO = drinkCategoryService.findById(id);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<DrinkCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException{
        DrinkCategoryDTO drinkCategoryDTO = drinkCategoryService.findByName(name);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @PostMapping
    public ResponseEntity<DrinkCategoryDTO> insert(@Valid @RequestBody DrinkCategoryDTO drinkCategoryDTO) {
        drinkCategoryDTO = drinkCategoryService.insert(drinkCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(drinkCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(drinkCategoryDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DrinkCategoryDTO> update(@Valid @PathVariable Long id, @RequestBody DrinkCategoryDTO drinkCategoryDTO) throws ResourceNotFoundException {
        drinkCategoryDTO = drinkCategoryService.update(id, drinkCategoryDTO);
        return ResponseEntity.ok().body(drinkCategoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DrinkCategoryDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        drinkCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
