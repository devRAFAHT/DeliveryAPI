package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishCategoryDTO;
import com.rafaelandrade.backend.resources.exceptions.StandardError;
import com.rafaelandrade.backend.services.DishCategoryService;
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

@Tag(name = "Dish Categories", description = "Endpoints for managing dish categories.")
@RestController
@RequestMapping(value = "/dish-categories")
public class DishCategoryResource {

    private final DishCategoryService dishCategoryService;

    @Autowired
    public DishCategoryResource(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Operation(summary = "List all dish categories", description = "Resource to list all dish categories.", responses = {
            @ApiResponse(responseCode = "200", description = "List of dish categories returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishCategoryDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DishCategoryDTO>> findAll(Pageable pageable) {
        Page<DishCategoryDTO> categories = dishCategoryService.findAll(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @Operation(summary = "Search for a dish category by ID", description = "Resource to search for an existing dish category by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DishCategoryDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishCategoryDTO dishCategoryDTO = dishCategoryService.findById(id);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @Operation(summary = "Search for a dish category by Name", description = "Resource to search for an existing dish category by its Name.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish category found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<DishCategoryDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DishCategoryDTO dishCategoryDTO = dishCategoryService.findByName(name);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @Operation(summary = "Create a new dish category", description = "Resource to create a new dish category.", responses = {
            @ApiResponse(responseCode = "201", description = "Dish category created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishCategoryDTO.class))),
            @ApiResponse(responseCode = "409", description = "The dish category is already registered in the system.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Category not registered due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<DishCategoryDTO> insert(@Valid @RequestBody DishCategoryDTO dishCategoryDTO) {
        dishCategoryDTO = dishCategoryService.insert(dishCategoryDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishCategoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishCategoryDTO);
    }

    @Operation(summary = "Update the name of a dish category", description = "Resource to update the name of a dish category.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish category updated successfully."),
            @ApiResponse(responseCode = "404", description = "Dish category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<DishCategoryDTO> update(@PathVariable Long id, @Valid @RequestBody DishCategoryDTO dishCategoryDTO) throws ResourceNotFoundException {
        dishCategoryDTO = dishCategoryService.update(id, dishCategoryDTO);
        return ResponseEntity.ok().body(dishCategoryDTO);
    }

    @Operation(summary = "Delete a dish category", description = "Delete a dish category by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Dish category deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Dish category not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}