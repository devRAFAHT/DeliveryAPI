package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.ItemDTO;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.entities.Menu;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.repositories.DrinkRepository;
import com.rafaelandrade.backend.repositories.ItemRepository;
import com.rafaelandrade.backend.repositories.MenuRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final ItemRepository itemRepository;

    public MenuService(MenuRepository menuRepository, DishRepository dishRepository, DrinkRepository drinkRepository, ItemRepository itemRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.map(menu -> new MenuDTO(menu, menu.getItems()));
    }

    @Transactional(readOnly = true)
    public MenuDTO findByCategory(String name) throws ResourceNotFoundException {
        Optional<Menu> menuObj = menuRepository.findByCategory(name);
        Menu menuEntity = menuObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional(readOnly = true)
    public MenuDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Menu> menuObj = menuRepository.findById(id);
        Menu menuEntity = menuObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional
    public MenuDTO insert(MenuDTO menuDTO) throws ResourceNotFoundException {
        Menu menuEntity = new Menu();
        copyDtoToEntity(menuDTO, menuEntity);
        menuEntity = menuRepository.save(menuEntity);
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional
    public MenuDTO update(Long id, MenuDTO menuDTO) throws ResourceNotFoundException {
        try {
            Menu menuEntity = menuRepository.getReferenceById(id);
            copyDtoToEntity(menuDTO, menuEntity);
            menuEntity = menuRepository.save(menuEntity);
            return new MenuDTO(menuEntity, menuEntity.getItems());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!menuRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            menuRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(MenuDTO menuDTO, Menu menuEntity) throws ResourceNotFoundException {
        menuEntity.setCategory(menuDTO.getCategory());
        menuEntity.setSaleStatus(menuDTO.getSaleStatus());

        menuEntity.getItems().clear();
        for (ItemDTO itemDTO : menuDTO.getItems()) {
            Optional<Item> itemObj = itemRepository.findById(itemDTO.getId());
            itemObj.orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found."));
            Item item = itemObj.get();
            menuEntity.getItems().add(item);

            item.setMenu(menuEntity);
        }
    }
}