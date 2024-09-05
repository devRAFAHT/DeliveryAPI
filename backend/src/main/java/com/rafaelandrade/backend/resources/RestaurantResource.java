package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.RestaurantDTO;
import com.rafaelandrade.backend.services.RestaurantService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Restaurant", description = "Endpoints for managing restaurants.")
@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantResource {

    private final RestaurantService restaurantService;

    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "List all restaurants", description = "Retrieve a paginated list of all restaurants.", responses = {
            @ApiResponse(responseCode = "200", description = "List of restaurants returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<RestaurantDTO>> findAll(Pageable pageable) {
        Page<RestaurantDTO> restaurants = restaurantService.findAll(pageable);
        return ResponseEntity.ok().body(restaurants);
    }

    @Operation(summary = "Find a restaurant by ID", description = "Retrieve a restaurant by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        RestaurantDTO restaurantDTO = restaurantService.findById(id);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @Operation(summary = "Find a restaurant by name", description = "Retrieve a restaurant by its name.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<RestaurantDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        RestaurantDTO restaurantDTO = restaurantService.findByName(name);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @Operation(summary = "Create a new restaurant", description = "Add a new restaurant to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<RestaurantDTO> insert(@Valid @RequestBody RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        restaurantDTO = restaurantService.insert(restaurantDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurantDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(restaurantDTO);
    }

    @Operation(summary = "Update an existing restaurant", description = "Modify an existing restaurant using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<RestaurantDTO> update(@Valid @PathVariable Long id, @RequestBody RestaurantDTO restaurantDTO) throws ResourceNotFoundException {
        restaurantDTO = restaurantService.update(id, restaurantDTO);
        return ResponseEntity.ok().body(restaurantDTO);
    }

    @Operation(summary = "Delete a restaurant", description = "Remove a restaurant from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}