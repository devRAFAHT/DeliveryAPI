package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.services.MenuService;
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

@Tag(name = "Menu", description = "Endpoints for managing menus.")
@RestController
@RequestMapping(value = "/menus")
public class MenuResource {

    private final MenuService menuService;

    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "List all menus", description = "Retrieve a paginated list of all menus.", responses = {
            @ApiResponse(responseCode = "200", description = "List of menus returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<MenuDTO>> findAll(Pageable pageable) {
        Page<MenuDTO> menus = menuService.findAll(pageable);
        return ResponseEntity.ok().body(menus);
    }

    @Operation(summary = "Find a menu by ID", description = "Retrieve a menu by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Menu found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDTO.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<MenuDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        MenuDTO menuDTO = menuService.findById(id);
        return ResponseEntity.ok().body(menuDTO);
    }

    @Operation(summary = "Find menus by category", description = "Retrieve menus by their category name.", responses = {
            @ApiResponse(responseCode = "200", description = "Menu(s) found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDTO.class))),
            @ApiResponse(responseCode = "404", description = "Menu(s) not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<MenuDTO> findByCategory(@RequestParam String name) throws ResourceNotFoundException {
        MenuDTO menuDTO = menuService.findByCategory(name);
        return ResponseEntity.ok().body(menuDTO);
    }

    @Operation(summary = "Create a new menu", description = "Add a new menu to the system.", responses = {
            @ApiResponse(responseCode = "201", description = "Menu created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PostMapping
    public ResponseEntity<MenuDTO> insert(@Valid @RequestBody MenuDTO menuDTO) throws ResourceNotFoundException {
        menuDTO = menuService.insert(menuDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(menuDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(menuDTO);
    }

    @Operation(summary = "Update an existing menu", description = "Modify an existing menu using its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Menu updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDTO.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<MenuDTO> update(@Valid @PathVariable Long id, @RequestBody MenuDTO menuDTO) throws ResourceNotFoundException {
        menuDTO = menuService.update(id, menuDTO);
        return ResponseEntity.ok().body(menuDTO);
    }

    @Operation(summary = "Delete a menu", description = "Remove a menu from the system using its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Menu deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Menu not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}