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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;

    public MenuService(MenuRepository menuRepository, DishRepository dishRepository, DrinkRepository drinkRepository, ItemRepository itemRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        logger.info("Fetching all menus with pageable: {}", pageable);
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.map(menu -> {
            logger.info("Fetched menu: {}", menu);
            return new MenuDTO(menu, menu.getItems());
        });
    }

    @Transactional(readOnly = true)
    public MenuDTO findByCategory(String name) throws ResourceNotFoundException {
        logger.info("Finding menu by category: {}", name);
        Optional<Menu> menuObj = menuRepository.findByCategory(name);
        Menu menuEntity = menuObj.orElseThrow(() -> {
            logger.error("Menu with category '{}' not found.", name);
            return new ResourceNotFoundException("Menu with category '" + name + "' not found.");
        });
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional(readOnly = true)
    public MenuDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding menu by id: {}", id);
        Optional<Menu> menuObj = menuRepository.findById(id);
        Menu menuEntity = menuObj.orElseThrow(() -> {
            logger.error("Menu with id {} not found.", id);
            return new ResourceNotFoundException("Menu with id " + id + " not found.");
        });
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional
    public MenuDTO insert(MenuDTO menuDTO) throws ResourceNotFoundException {
        logger.info("Inserting new menu with details: {}", menuDTO);
        Menu menuEntity = new Menu();
        checksIfAssociatedEntitiesExist(menuDTO);
        copyDtoToEntity(menuDTO, menuEntity);
        menuEntity = menuRepository.save(menuEntity);
        logger.info("Inserted new menu: {}", menuEntity);
        return new MenuDTO(menuEntity, menuEntity.getItems());
    }

    @Transactional
    public MenuDTO update(Long id, MenuDTO menuDTO) throws ResourceNotFoundException {
        logger.info("Updating menu with id {} and details: {}", id, menuDTO);
        try {
            Menu menuEntity = menuRepository.getReferenceById(id);
            checksIfAssociatedEntitiesExist(menuDTO);
            copyDtoToEntity(menuDTO, menuEntity);
            menuEntity = menuRepository.save(menuEntity);
            logger.info("Updated menu: {}", menuEntity);
            return new MenuDTO(menuEntity, menuEntity.getItems());
        } catch (EntityNotFoundException e) {
            logger.error("Error updating menu with id {}: {}", id, e.getMessage());
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting menu with id {}", id);
        if (!menuRepository.existsById(id)) {
            logger.error("Id not found: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            menuRepository.deleteById(id);
            logger.info("Deleted menu with id {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error deleting menu with id {}: {}", id, e.getMessage());
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(MenuDTO menuDTO, Menu menuEntity) throws ResourceNotFoundException {
        logger.debug("Copying MenuDTO to Menu entity: {}", menuDTO);
        menuEntity.setCategory(menuDTO.getCategory());
        menuEntity.setSaleStatus(menuDTO.getSaleStatus());

        menuEntity.getItems().clear();
        for (ItemDTO itemDTO : menuDTO.getItems()) {
            logger.debug("Copying item with id {} to menu", itemDTO.getId());
            Item item = itemRepository.getReferenceById(itemDTO.getId());
            menuEntity.getItems().add(item);
            item.setMenu(menuEntity);
        }
    }

    private void checksIfAssociatedEntitiesExist(MenuDTO menuDTO) throws ResourceNotFoundException {
        for (ItemDTO itemDTO : menuDTO.getItems()) {
            Long itemId = itemDTO.getId();
            logger.debug("Checking if item with id {} exists", itemId);
            if (!itemRepository.existsById(itemId)) {
                logger.error("Item with id {} not found.", itemId);
                throw new ResourceNotFoundException("Item with id " + itemId + " not found.");
            }
        }
    }
}