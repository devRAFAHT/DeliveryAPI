package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AssessmentDTO;
import com.rafaelandrade.backend.dto.AssessmentUpdateDTO;
import com.rafaelandrade.backend.entities.Assessment;
import com.rafaelandrade.backend.services.AssessmentService;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.InvalidInputException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/assessments")
public class AssessmentResource {

    private final AssessmentService assessmentService;

    public AssessmentResource(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    ResponseEntity<Page<AssessmentDTO>> findAll(Pageable pageable){
        Page<AssessmentDTO> assessments = assessmentService.findAll(pageable);
        return ResponseEntity.ok().body(assessments);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<AssessmentDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AssessmentDTO assessmentDTO = assessmentService.findById(id);
        return ResponseEntity.ok().body(assessmentDTO);
    }

    @PostMapping
    public ResponseEntity<AssessmentDTO> insert(@Valid @RequestBody AssessmentDTO assessmentDTO) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        assessmentDTO = assessmentService.insert(assessmentDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(assessmentDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(assessmentDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AssessmentDTO> update(@Valid @PathVariable Long id, @RequestBody AssessmentUpdateDTO assessmentUpdateDTO) throws ResourceNotFoundException {
        AssessmentDTO assessmentDTO = assessmentService.update(id, assessmentUpdateDTO);
        return ResponseEntity.ok().body(assessmentDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AssessmentDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        assessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
