package com.rafaelandrade.backend.services;

import com.rafaelandrade.backend.dto.BagDTO;
import com.rafaelandrade.backend.dto.ItemDTO;
import com.rafaelandrade.backend.entities.Bag;
import com.rafaelandrade.backend.entities.Item;
import com.rafaelandrade.backend.repositories.BagRepository;
import com.rafaelandrade.backend.repositories.ItemRepository;
import com.rafaelandrade.backend.services.exceptions.DatabaseException;
import com.rafaelandrade.backend.services.exceptions.ResourceNotFoundException;
import com.rafaelandrade.backend.services.util.CalculateDiscount;
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

    private final ItemRepository itemRepository;

    public BagService(BagRepository bagRepository, ItemRepository itemRepository) {
        this.bagRepository = bagRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public Page<BagDTO> findAll(Pageable pageable) {
        Page<Bag> bags = bagRepository.findAll(pageable);
        return bags.map(bag -> new BagDTO(bag, bag.getItems()));
    }

    @Transactional(readOnly = true)
    public BagDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Bag> bagObj = bagRepository.findById(id);
        Bag bagEntity = bagObj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new BagDTO(bagEntity, bagEntity.getItems());
    }

    @Transactional
    public BagDTO insert(BagDTO bagDTO) throws ResourceNotFoundException {
        Bag bagEntity = new Bag();
        copyDtoToEntity(bagDTO, bagEntity);
        bagEntity = bagRepository.save(bagEntity);
        return new BagDTO(bagEntity, bagEntity.getItems());
    }

    @Transactional
    public BagDTO update(Long id, BagDTO bagDTO) throws ResourceNotFoundException {
        try {
            Bag bagEntity = bagRepository.getReferenceById(id);
            copyDtoToEntity(bagDTO, bagEntity);
            bagEntity = bagRepository.save(bagEntity);
            return new BagDTO(bagEntity, bagEntity.getItems());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        if (!bagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            bagRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(BagDTO bagDTO, Bag bagEntity) throws ResourceNotFoundException {
        bagEntity.setTotalPrice(bagDTO.getTotalPrice());
        bagEntity.setDiscount(bagDTO.getDiscount());

        Integer quantity = 0;
        BigDecimal discount = BigDecimal.valueOf(0.00);
        BigDecimal totalPrice = BigDecimal.valueOf(0.00);

        bagEntity.getItems().clear();
        for (ItemDTO itemDTO : bagDTO.getItems()) {
            Optional<Item> itemObj = itemRepository.findById(itemDTO.getId());
            Item item = itemObj.orElseThrow(() -> new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found."));
            bagEntity.getItems().add(item);
            quantity++;
            discount = discount.add(CalculateDiscount.calculateDiscountInMoneyWithOriginalPriceAndCurrentPrice(item.getOriginalPrice(), item.getCurrentPrice()));
            totalPrice = totalPrice.add(item.getCurrentPrice());
        }

        bagEntity.setQuantityOfItems(quantity);
        bagEntity.setDiscount(discount);
        bagEntity.setTotalPrice(totalPrice);
    }
}
