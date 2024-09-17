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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BagService {

    private static final Logger logger = LoggerFactory.getLogger(BagService.class);

    private final BagRepository bagRepository;
    private final ItemRepository itemRepository;

    public BagService(BagRepository bagRepository, ItemRepository itemRepository) {
        this.bagRepository = bagRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public Page<BagDTO> findAll(Pageable pageable) {
        logger.info("Finding all bags with pageable: {}", pageable);
        Page<Bag> bags = bagRepository.findAll(pageable);
        return bags.map(bag -> new BagDTO(bag, bag.getItems()));
    }

    @Transactional(readOnly = true)
    public BagDTO findById(Long id) throws ResourceNotFoundException {
        logger.info("Finding bag by id: {}", id);
        Optional<Bag> bagObj = bagRepository.findById(id);
        Bag bagEntity = bagObj.orElseThrow(() -> {
            logger.error("Bag with id {} not found", id);
            return new ResourceNotFoundException("Entity not found");
        });
        return new BagDTO(bagEntity, bagEntity.getItems());
    }

    @Transactional
    public BagDTO insert(BagDTO bagDTO) throws ResourceNotFoundException {
        logger.info("Inserting new bag: {}", bagDTO);
        Bag bagEntity = new Bag();
        checksIfAssociatedEntitiesExist(bagDTO);
        copyDtoToEntity(bagDTO, bagEntity);
        bagEntity = bagRepository.save(bagEntity);
        return new BagDTO(bagEntity, bagEntity.getItems());
    }

    @Transactional
    public BagDTO update(Long id, BagDTO bagDTO) throws ResourceNotFoundException {
        logger.info("Updating bag with id: {}", id);
        try {
            Bag bagEntity = bagRepository.getReferenceById(id);
            checksIfAssociatedEntitiesExist(bagDTO);
            copyDtoToEntity(bagDTO, bagEntity);
            bagEntity = bagRepository.save(bagEntity);
            return new BagDTO(bagEntity, bagEntity.getItems());
        } catch (EntityNotFoundException e) {
            logger.error("Id not found: {}", id, e);
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting bag with id: {}", id);
        if (!bagRepository.existsById(id)) {
            logger.error("Id not found: {}", id);
            throw new ResourceNotFoundException("Id not found: " + id);
        }

        try {
            bagRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            logger.error("Integrity violation while deleting id: {}", id, e);
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(BagDTO bagDTO, Bag bagEntity) throws ResourceNotFoundException {
        logger.debug("Copying DTO to entity: {}", bagDTO);
        bagEntity.setTotalPrice(bagDTO.getTotalPrice());
        bagEntity.setDiscount(bagDTO.getDiscount());

        Integer quantity = 0;
        BigDecimal discount = BigDecimal.valueOf(0.00);
        BigDecimal totalPrice = BigDecimal.valueOf(0.00);

        bagEntity.getItems().clear();
        for (ItemDTO itemDTO : bagDTO.getItems()) {
            Item item = itemRepository.getReferenceById(itemDTO.getId());
            bagEntity.getItems().add(item);
            quantity++;
            discount = discount.add(CalculateDiscount.calculateDiscountInMoneyWithOriginalPriceAndCurrentPrice(item.getOriginalPrice(), item.getCurrentPrice()));
            totalPrice = totalPrice.add(item.getCurrentPrice());
        }

        bagEntity.setQuantityOfItems(quantity);
        bagEntity.setDiscount(discount);
        bagEntity.setTotalPrice(totalPrice);
    }

    private void checksIfAssociatedEntitiesExist(BagDTO bagDTO) throws ResourceNotFoundException {
        logger.debug("Checking if associated entities exist for DTO: {}", bagDTO);
        for (ItemDTO itemDTO : bagDTO.getItems()) {
            long itemId = itemDTO.getId();
            if (!itemRepository.existsById(itemId)) {
                logger.error("Item with id {} not found.", itemId);
                throw new ResourceNotFoundException("Item with id " + itemDTO.getId() + " not found.");
            }
        }
    }
}