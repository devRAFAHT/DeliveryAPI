package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.RestaurantCategoryDTO;
import com.rafaelandrade.backend.resources.exceptions.StandardError;
import com.rafaelandrade.backend.services.RestaurantCategoryService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Restaurant Categories", description = "Endpoints for managing restaurant categories.")
@RestController
@RequestMapping(value = "/restaurant-categories")
public class RestaurantCategoryResource {

    private final RestaurantCategoryService restaurantCategoryService;

    @Autowired
    public RestaurantCategoryResource(RestaurantCategoryService restaurantCategoryService) {
        this.restaurantCategoryService = restaurantCategoryService;
    }

    @Operation(summary = "List all restaurant categories", description = "Resource to list all restaurant categories.", responses = {
            @ApiResponse(responseCode = "200", description = "List of restaurant categories returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantCategoryDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<RestaurantCategoryDTO>> findAll(Pageable pageable){
        Page<RestaurantCategoryDTO> categories = restaurantCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @Operation(summary = "Search for a restaurant category by ID", description = "Resource to search for an existing restaurant category by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        RestaurantCategoryDTO restaurantCategoryDTO = restaurantCategoryService.findById(id);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @Operation(summary = "Search for a restaurant category by Name", description = "Resource to search for an existing restaurant category by its Name.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<RestaurantCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        RestaurantCategoryDTO restaurantCategoryDTO = restaurantCategoryService.findByName(name);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @Operation(summary = "Create a new restaurant category", description = "Resource to create a new restaurant category.", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant category created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantCategoryDTO.class))),
            @ApiResponse(responseCode = "409", description = "The restaurant category is already registered in the system.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Category not registered due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<RestaurantCategoryDTO> insert(@Valid @RequestBody RestaurantCategoryDTO restaurantCategoryDTO) {
        restaurantCategoryDTO = restaurantCategoryService.insert(restaurantCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurantCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(restaurantCategoryDTO);
    }

    @Operation(summary = "Update the name of a restaurant category", description = "Resource to update the name of a restaurant category.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant category updated successfully."),
            @ApiResponse(responseCode = "404", description = "Restaurant category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<RestaurantCategoryDTO> update(@PathVariable Long id, @Valid @RequestBody RestaurantCategoryDTO restaurantCategoryDTO) throws ResourceNotFoundException {
        restaurantCategoryDTO = restaurantCategoryService.update(id, restaurantCategoryDTO);
        return ResponseEntity.ok().body(restaurantCategoryDTO);
    }

    @Operation(summary = "Delete a restaurant category", description = "Delete a restaurant category by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Restaurant category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        restaurantCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
