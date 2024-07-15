package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.DishDTO;
import com.rafaelandrade.backend.dto.DrinkDTO;
import com.rafaelandrade.backend.dto.BagDTO;
import com.rafaelandrade.backend.entities.Dish;
import com.rafaelandrade.backend.entities.Drink;
import com.rafaelandrade.backend.entities.Bag;
import com.rafaelandrade.backend.repositories.BagRepository;
import com.rafaelandrade.backend.repositories.DishRepository;
import com.rafaelandrade.backend.repositories.DrinkRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.util.CalculateDiscount;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BagService {

    private final BagRepository bagRepository;

    private final DishRepository dishRepository;

    private final DrinkRepository drinkRepository;

    public BagService(BagRepository bagRepository, DishRepository dishRepository, DrinkRepository drinkRepository) {
        this.bagRepository = bagRepository;
        this.dishRepository = dishRepository;
        this.drinkRepository = drinkRepository;
    }

    @Transactional(readOnly = true)
    public Page<BagDTO> findAll(Pageable pageable) {
        Page<Bag> bags = bagRepository.findAll(pageable);
        return bags.map(bag -> new BagDTO(bag, bag.getDishes(), bag.getDrinks()));
    }

    @Transactional(readOnly = true)
    public BagDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Bag> bagObj = bagRepository.findById(id);
        Bag bagEntity = bagObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new BagDTO(bagEntity, bagEntity.getDishes(), bagEntity.getDrinks());
    }

    @Transactional
    public BagDTO insert(BagDTO bagDTO) throws ResourceNotFoundException {
        Bag bagEntity = new Bag();
        copyDtoToEntity(bagDTO, bagEntity);
        bagEntity = bagRepository.save(bagEntity);
        return new BagDTO(bagEntity, bagEntity.getDishes(), bagEntity.getDrinks());
    }

    @Transactional
    public BagDTO update(Long id, BagDTO bagDTO) throws ResourceNotFoundException {
        try {
            Bag bagEntity = bagRepository.getReferenceById(id);
            copyDtoToEntity(bagDTO, bagEntity);
            bagEntity = bagRepository.save(bagEntity);
            return new BagDTO(bagEntity, bagEntity.getDishes(), bagEntity.getDrinks());
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if(!bagRepository.existsById(id)){
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            bagRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(BagDTO bagDTO, Bag bagEntity) throws ResourceNotFoundException {
        bagEntity.setTotalPrice(bagDTO.getTotalPrice());
        bagEntity.setDiscount(bagDTO.getDiscount());

        Integer quantity = 0;
        BigDecimal discount = BigDecimal.valueOf(0.00);
        BigDecimal totalPrice = BigDecimal.valueOf(0.00);

        bagEntity.getDishes().clear();
        for(DishDTO dishDTO : bagDTO.getDishes()){
            Optional<Dish> dishObj = dishRepository.findById(dishDTO.getId());
            dishObj.orElseThrow(() -> new ResourceNotFoundException("Dish with id " + dishDTO.getId() + " not found."));
            Dish dish = dishObj.get();
            bagEntity.getDishes().add(dish);
            quantity++;
            discount = discount.add(CalculateDiscount.calculateDiscountInMoneyWithOriginalPriceAndCurrentPrice(dish.getOriginalPrice(), dish.getCurrentPrice()));
            totalPrice = totalPrice.add(dish.getCurrentPrice());
        }

        bagEntity.getDrinks().clear();
        for(DrinkDTO drinkDTO : bagDTO.getDrinks()){
            Optional<Drink> drinkObj = drinkRepository.findById(drinkDTO.getId());
            drinkObj.orElseThrow(() -> new ResourceNotFoundException("Drink with id " + drinkDTO.getId() + " not found."));
            Drink drink = drinkObj.get();
            bagEntity.getDrinks().add(drink);
            quantity++;
            discount = discount.add(CalculateDiscount.calculateDiscountInMoneyWithOriginalPriceAndCurrentPrice(drink.getOriginalPrice(), drink.getCurrentPrice()));
            totalPrice = totalPrice.add(drink.getCurrentPrice());
        }

        bagEntity.setQuantityOfItems(quantity);
        bagEntity.setDiscount(discount);
        bagEntity.setTotalPrice(totalPrice);
    }
}