package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AssessmentDTO;
import com.rafaelandrade.backend.dto.AssessmentUpdateDTO;
import com.rafaelandrade.backend.services.AssessmentService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
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

@Tag(name = "Assessment", description = "Endpoints for managing assessments.")
@RestController
@RequestMapping(value = "/assessments")
public class AssessmentResource {

    private final AssessmentService assessmentService;

    public AssessmentResource(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @Operation(summary = "List all assessments", description = "Resource to list all assessments.", responses = {
            @ApiResponse(responseCode = "200", description = "List of assessments returned successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<AssessmentDTO>> findAll(Pageable pageable) {
        Page<AssessmentDTO> assessments = assessmentService.findAll(pageable);
        return ResponseEntity.ok().body(assessments);
    }

    @Operation(summary = "Search for an assessment by ID", description = "Resource to search for an assessment by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Assessment found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AssessmentDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AssessmentDTO assessmentDTO = assessmentService.findById(id);
        return ResponseEntity.ok().body(assessmentDTO);
    }

    @Operation(summary = "Create a new assessment", description = "Resource to create a new assessment.", responses = {
            @ApiResponse(responseCode = "201", description = "Assessment created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidInputException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @PostMapping
    public ResponseEntity<AssessmentDTO> insert(@Valid @RequestBody AssessmentDTO assessmentDTO)
            throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        assessmentDTO = assessmentService.insert(assessmentDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(assessmentDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(assessmentDTO);
    }

    @Operation(summary = "Update an assessment", description = "Resource to update an existing assessment.", responses = {
            @ApiResponse(responseCode = "200", description = "Assessment updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssessmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assessment not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidInputException.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<AssessmentDTO> update(@PathVariable Long id, @Valid @RequestBody AssessmentUpdateDTO assessmentUpdateDTO)
            throws ResourceNotFoundException, InvalidInputException {
        AssessmentDTO assessmentDTO = assessmentService.update(id, assessmentUpdateDTO);
        return ResponseEntity.ok().body(assessmentDTO);
    }

    @Operation(summary = "Delete an assessment", description = "Delete an assessment by its ID.", responses = {
            @ApiResponse(responseCode = "204", description = "Assessment deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Assessment not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class))),
            @ApiResponse(responseCode = "500", description = "Database error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatabaseException.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        assessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}