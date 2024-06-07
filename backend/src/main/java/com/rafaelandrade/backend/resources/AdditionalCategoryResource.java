package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AdditionalCategoryDTO;
import com.rafaelandrade.backend.services.AdditionalCategoryService;
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
@RequestMapping(value = "/additional-categories")
public class AdditionalCategoryResource {

    @Autowired
    private AdditionalCategoryService additionalCategoryService;

    @GetMapping
    public ResponseEntity<Page<AdditionalCategoryDTO>> findAll(Pageable pageable){
        Page<AdditionalCategoryDTO> categories = additionalCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdditionalCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AdditionalCategoryDTO additionalCategoryDTO = additionalCategoryService.findById(id);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<AdditionalCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException{
        AdditionalCategoryDTO additionalCategoryDTO = additionalCategoryService.findByName(name);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @PostMapping
    public ResponseEntity<AdditionalCategoryDTO> insert(@Valid @RequestBody AdditionalCategoryDTO additionalCategoryDTO) {
        additionalCategoryDTO = additionalCategoryService.insert(additionalCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(additionalCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(additionalCategoryDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AdditionalCategoryDTO> update(@Valid @PathVariable Long id, @RequestBody AdditionalCategoryDTO additionalCategoryDTO) throws ResourceNotFoundException {
        additionalCategoryDTO = additionalCategoryService.update(id, additionalCategoryDTO);
        return ResponseEntity.ok().body(additionalCategoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AdditionalCategoryDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        additionalCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
