package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.services.DishService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Dish", description = "Endpoints for managing dishes.")
@RestController
@RequestMapping(value = "/dishes")
public class DishResource {

    private final DishService dishService;

    public DishResource(DishService dishService) {
        this.dishService = dishService;
    }

    @Operation(summary = "List all dishes", description = "Retrieve a paginated list of all dishes.", responses = {
            @ApiResponse(responseCode = "200", description = "List of dishes returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DishDTO>> findAll(Pageable pageable) {
        Page<DishDTO> dishes = dishService.findAll(pageable);
        return ResponseEntity.ok().body(dishes);
    }

    @Operation(summary = "Find a dish by ID", description = "Retrieve a dish by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<DishDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        DishDTO dishDTO = dishService.findById(id);
        return ResponseEntity.ok().body(dishDTO);
    }

    @Operation(summary = "Find a dish by name", description = "Retrieve a dish by its name.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<DishDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        DishDTO dishDTO = dishService.findByName(name);
        return ResponseEntity.ok().body(dishDTO);
    }

    @Operation(summary = "Create a new dish", description = "Add a new dish to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Dish created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<DishDTO> insert(@Valid @RequestBody DishDTO dishDTO) throws ResourceNotFoundException {
        dishDTO = dishService.insert(dishDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dishDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(dishDTO);
    }

    @Operation(summary = "Update an existing dish", description = "Modify an existing dish using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Dish updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<DishDTO> update(@Valid @PathVariable Long id, @RequestBody DishDTO dishDTO) throws ResourceNotFoundException {
        dishDTO = dishService.update(id, dishDTO);
        return ResponseEntity.ok().body(dishDTO);
    }

    @Operation(summary = "Delete a dish", description = "Remove a dish from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Dish deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Dish not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }
}