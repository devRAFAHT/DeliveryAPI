package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.services.RestaurantService;
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
@RequestMapping(value = "/restaurants")
public class RestaurantResource {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    ResponseEntity<Page<RestaurantDTO>> findAll(Pageable pageable){
        Page<RestaurantDTO> restaurants = restaurantService.findAll(pageable);
        return ResponseEntity.ok().body(restaurants);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<RestaurantDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        RestaurantDTO restaurantDTO = restaurantService.findById(id);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @GetMapping(value = "/search")
    ResponseEntity<RestaurantDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        RestaurantDTO restaurantDTO = restaurantService.findByName(name);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> insert(@Valid @RequestBody RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        restaurantDTO = restaurantService.insert(restaurantDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurantDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(restaurantDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> update(@Valid @PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        restaurantDTO = restaurantService.update(id, restaurantDTO);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
