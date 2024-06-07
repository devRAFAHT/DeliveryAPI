package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.AdditionalDTO;
import com.rafaelandrade.backend.services.AdditionalService;
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
@RequestMapping(value = "/additional")
public class AdditionalResource {

    @Autowired
    private AdditionalService additionalService;

    @GetMapping
    ResponseEntity<Page<AdditionalDTO>> findAll(Pageable pageable){
        Page<AdditionalDTO> additionalList = additionalService.findAll(pageable);
        return ResponseEntity.ok().body(additionalList);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<AdditionalDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        AdditionalDTO additionalDTO = additionalService.findById(id);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @GetMapping(value = "/search")
    ResponseEntity<AdditionalDTO> findByName(@RequestParam String name) throws ResourceNotFoundException {
        AdditionalDTO additionalDTO = additionalService.findByName(name);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @PostMapping
    public ResponseEntity<AdditionalDTO> insert(@Valid @RequestBody AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        additionalDTO = additionalService.insert(additionalDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(additionalDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(additionalDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AdditionalDTO> update(@Valid @PathVariable Long id, @RequestBody AdditionalDTO additionalDTO) throws ResourceNotFoundException {
        additionalDTO = additionalService.update(id, additionalDTO);
        return ResponseEntity.ok().body(additionalDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AdditionalDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        additionalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
