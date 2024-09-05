package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishVariationDTO;
import com.rafaelandrade.backend.services.DishVariationService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/dishVariationes")
public class DishVariationResource {

    private final DishVariationService dishVariationService;

    public DishVariationResource(DishVariationService dishVariationService) {
        this.dishVariationService = dishVariationService;
    }

    @GetMapping
    ResponseEntity<Page<DishVariationDTO>> findAll(Pageable pageable){
        Page<DishVariationDTO> dishVariationes = dishVariationService.findAll(pageable);
        return ResponseEntity.ok().body(dishVariationes);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<DishVariationDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishVariationDTO dishVariationDTO = dishVariationService.findById(id);
        return ResponseEntity.ok().body(dishVariationDTO);
    }

    @PostMapping
    public ResponseEntity<DishVariationDTO> insert(@Valid @RequestBody DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        dishVariationDTO = dishVariationService.insert(dishVariationDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishVariationDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishVariationDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DishVariationDTO> update(@Valid @PathVariable Long id, @RequestBody DishVariationDTO dishVariationDTO) throws ResourceNotFoundException {
        dishVariationDTO = dishVariationService.update(id, dishVariationDTO);
        return ResponseEntity.ok().body(dishVariationDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DishVariationDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishVariationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
