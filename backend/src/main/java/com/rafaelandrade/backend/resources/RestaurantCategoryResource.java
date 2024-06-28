package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.services.RestaurantCategoryService;
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
@RequestMapping(value = "/restaurant-categories")
public class RestaurantCategoryResource {

    @Autowired
    private RestaurantCategoryService restaurantCategoryService;

    @GetMapping
    public ResponseEntity<Page<RestaurantCategoryDTO>> findAll(Pageable pageable){
        Page<RestaurantCategoryDTO> categories = restaurantCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        RestaurantCategoryDTO restaurantCategoryDTO = restaurantCategoryService.findById(id);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<RestaurantCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException{
        RestaurantCategoryDTO restaurantCategoryDTO = restaurantCategoryService.findByName(name);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @PostMapping
    public ResponseEntity<RestaurantCategoryDTO> insert(@Valid @RequestBody RestaurantCategoryDTO restaurantCategoryDTO) {
        restaurantCategoryDTO = restaurantCategoryService.insert(restaurantCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurantCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(restaurantCategoryDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RestaurantCategoryDTO> update(@Valid @PathVariable Long id, @RequestBody RestaurantCategoryDTO restaurantCategoryDTO) throws ResourceNotFoundException {
        restaurantCategoryDTO = restaurantCategoryService.update(id, restaurantCategoryDTO);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RestaurantCategoryDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        restaurantCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
