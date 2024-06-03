package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.services.DishService;
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
@RequestMapping(value = "/dishes")
public class DishResource {

    @Autowired
    private DishService dishService;

    @GetMapping
    ResponseEntity<Page<DishDTO>> findAll(Pageable pageable){
        Page<DishDTO> dishes = dishService.findAll(pageable);
        return ResponseEntity.ok().body(dishes);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<DishDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishDTO dishDTO = dishService.findById(id);
        return ResponseEntity.ok().body(dishDTO);
    }

    @GetMapping(value = "/search")
    ResponseEntity<DishDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DishDTO dishDTO = dishService.findByName(name);
        return ResponseEntity.ok().body(dishDTO);
    }

    @PostMapping
    public ResponseEntity<DishDTO> insert(@Valid @RequestBody DishDTO dishDTO) throws ResourceNotFoundException {
        dishDTO = dishService.insert(dishDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DishDTO> update(@Valid @PathVariable Long id, @RequestBody DishDTO dishDTO) throws ResourceNotFoundException {
        dishDTO = dishService.update(id, dishDTO);
        return ResponseEntity.ok().body(dishDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DishDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
