package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.services.DrinkService;
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
@RequestMapping(value = "/drinks")
public class DrinkResource {

    @Autowired
    private DrinkService drinkService;

    @GetMapping
    ResponseEntity<Page<DrinkDTO>> findAll(Pageable pageable){
        Page<DrinkDTO> drinks = drinkService.findAll(pageable);
        return ResponseEntity.ok().body(drinks);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<DrinkDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DrinkDTO drinkDTO = drinkService.findById(id);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @GetMapping(value = "/search")
    ResponseEntity<DrinkDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DrinkDTO drinkDTO = drinkService.findByName(name);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @PostMapping
    public ResponseEntity<DrinkDTO> insert(@Valid @RequestBody DrinkDTO drinkDTO) throws ResourceNotFoundException {
        drinkDTO = drinkService.insert(drinkDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(drinkDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(drinkDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> update(@Valid @PathVariable Long id, @RequestBody DrinkDTO drinkDTO) throws ResourceNotFoundException {
        drinkDTO = drinkService.update(id, drinkDTO);
        return ResponseEntity.ok().body(drinkDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        drinkService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
