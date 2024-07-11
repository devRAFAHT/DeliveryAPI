package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.dto.MenuDTO;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Menu;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.repositories.DrinkRepository;
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

    private final DishRepository dishRepository;

    private final DrinkRepository drinkRepository;

    public MenuService(MenuRepository menuRepository, DishRepository dishRepository, DrinkRepository drinkRepository) {
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
    }

    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.map(menu -> new MenuDTO(menu, menu.getDishes(), menu.getDrinks()));
    }

    @Transactional(readOnly = true)
    public MenuDTO findByCategory(String name) throws ResourceNotFoundException {
        Optional<Menu> menuObj = menuRepository.findByCategory(name);
        Menu menuEntity = menuObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MenuDTO(menuEntity, menuEntity.getDishes(), menuEntity.getDrinks());
    }

    @Transactional(readOnly = true)
    public MenuDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Menu> menuObj = menuRepository.findById(id);
        Menu menuEntity = menuObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MenuDTO(menuEntity, menuEntity.getDishes(), menuEntity.getDrinks());
    }

    @Transactional
    public MenuDTO insert(MenuDTO menuDTO) throws ResourceNotFoundException {
        Menu menuEntity = new Menu();
        copyDtoToEntity(menuDTO, menuEntity);
        menuEntity = menuRepository.save(menuEntity);
        return new MenuDTO(menuEntity, menuEntity.getDishes(), menuEntity.getDrinks());
    }

    @Transactional
    public MenuDTO update(Long id, MenuDTO menuDTO) throws ResourceNotFoundException {
        try {
            Menu menuEntity = menuRepository.getReferenceById(id);
            copyDtoToEntity(menuDTO, menuEntity);
            menuEntity = menuRepository.save(menuEntity);
            return new MenuDTO(menuEntity, menuEntity.getDishes(), menuEntity.getDrinks());
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

        menuEntity.getDishes().clear();
        for(DishDTO dishDTO : menuDTO.getDishes()){
            Optional<Dish> dishObj = dishRepository.findById(dishDTO.getId());
            dishObj.orElseThrow(() -> new ResourceNotFoundException("Dish with id " + dishDTO.getId() + " not found."));
            Dish dish = dishObj.get();
            dish.setSaleStatus(menuDTO.getSaleStatus());
            menuEntity.getDishes().add(dish);

            dish.setMenu(menuEntity);
        }

        menuEntity.getDrinks().clear();
        for(DrinkDTO drinkDTO : menuDTO.getDrinks()){
            Optional<Drink> drinkObj = drinkRepository.findById(drinkDTO.getId());
            drinkObj.orElseThrow(() -> new ResourceNotFoundException("Drink with id " + drinkDTO.getId() + " not found."));
            Drink drink = drinkObj.get();
            menuEntity.getDrinks().add(drink);

            drink.setMenu(menuEntity);
        }

    }
}