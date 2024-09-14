package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.RestaurantCreateDTO;
import com.rafaelandrade.backend.dto.RestaurantDetailsResponseDTO;
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDetailsResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<RestaurantDetailsResponseDTO>> findAll(Pageable pageable) {
        Page<RestaurantDetailsResponseDTO> restaurants = restaurantService.findAll(pageable);
        return ResponseEntity.ok().body(restaurants);
    }

    @Operation(summary = "Find a restaurant by ID", description = "Retrieve a restaurant by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RestaurantDetailsResponseDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        RestaurantDetailsResponseDTO restaurantDetailsDTO = restaurantService.findById(id);
        return ResponseEntity.ok().body(restaurantDetailsDTO);
    }

    @Operation(summary = "Find a restaurant by name", description = "Retrieve a restaurant by its name.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<RestaurantDetailsResponseDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        RestaurantDetailsResponseDTO restaurantDetailsDTO = restaurantService.findByName(name);
        return ResponseEntity.ok().body(restaurantDetailsDTO);
    }

    @Operation(summary = "Create a new restaurant", description = "Add a new restaurant to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<RestaurantDetailsResponseDTO> insert(@Valid @RequestBody RestaurantCreateDTO restaurantCreateDTO) throws ResourceNotFoundException {
        RestaurantDetailsResponseDTO restaurantDetailsDTO = restaurantService.insert(restaurantCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurantDetailsDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(restaurantDetailsDTO);
    }

    @Operation(summary = "Update an existing restaurant", description = "Modify an existing restaurant using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDetailsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<RestaurantDetailsResponseDTO> update(@Valid @PathVariable Long id, @RequestBody RestaurantCreateDTO restaurantCreateDTO) throws ResourceNotFoundException {
        RestaurantDetailsResponseDTO restaurantDetailsDTO = restaurantService.update(id, restaurantCreateDTO);
        return ResponseEntity.ok().body(restaurantDetailsDTO);
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
