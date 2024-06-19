package com.rafaelandrade.backend.resources;

import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.services.MenuService;
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
@RequestMapping(value = "/menus")
public class MenuResource {

    @Autowired
    private MenuService menuService;

    @GetMapping
    ResponseEntity<Page<MenuDTO>> findAll(Pageable pageable){
        Page<MenuDTO> menus = menuService.findAll(pageable);
        return ResponseEntity.ok().body(menus);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<MenuDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
        MenuDTO menuDTO = menuService.findById(id);
        return ResponseEntity.ok().body(menuDTO);
    }

    @GetMapping(value = "/search")
    ResponseEntity<MenuDTO> findByCategory(@RequestParam String name) throws ResourceNotFoundException {
        MenuDTO menuDTO = menuService.findByCategory(name);
        return ResponseEntity.ok().body(menuDTO);
    }

    @PostMapping
    public ResponseEntity<MenuDTO> insert(@Valid @RequestBody MenuDTO menuDTO) throws ResourceNotFoundException {
        menuDTO = menuService.insert(menuDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(menuDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(menuDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MenuDTO> update(@Valid @PathVariable Long id, @RequestBody MenuDTO menuDTO) throws ResourceNotFoundException {
        menuDTO = menuService.update(id, menuDTO);
        return ResponseEntity.ok().body(menuDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MenuDTO> delete(@PathVariable Long id) throws ResourceNotFoundException, DatabaseException {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
