package com.apptium.service.impl;

import com.apptium.domain.ShoppingSessionRef;
import com.apptium.repository.ShoppingSessionRefRepository;
import com.apptium.service.ShoppingSessionRefService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoppingSessionRef}.
 */
@Service
@Transactional
public class ShoppingSessionRefServiceImpl implements ShoppingSessionRefService {

    private final Logger log = LoggerFactory.getLogger(ShoppingSessionRefServiceImpl.class);

    private final ShoppingSessionRefRepository shoppingSessionRefRepository;

    public ShoppingSessionRefServiceImpl(ShoppingSessionRefRepository shoppingSessionRefRepository) {
        this.shoppingSessionRefRepository = shoppingSessionRefRepository;
    }

    @Override
    public ShoppingSessionRef save(ShoppingSessionRef shoppingSessionRef) {
        log.debug("Request to save ShoppingSessionRef : {}", shoppingSessionRef);
        return shoppingSessionRefRepository.save(shoppingSessionRef);
    }

    @Override
    public Optional<ShoppingSessionRef> partialUpdate(ShoppingSessionRef shoppingSessionRef) {
        log.debug("Request to partially update ShoppingSessionRef : {}", shoppingSessionRef);

        return shoppingSessionRefRepository
            .findById(shoppingSessionRef.getId())
            .map(
                existingShoppingSessionRef -> {
                    if (shoppingSessionRef.getHref() != null) {
                        existingShoppingSessionRef.setHref(shoppingSessionRef.getHref());
                    }
                    if (shoppingSessionRef.getShoppingSessionId() != null) {
                        existingShoppingSessionRef.setShoppingSessionId(shoppingSessionRef.getShoppingSessionId());
                    }
                    if (shoppingSessionRef.getStatus() != null) {
                        existingShoppingSessionRef.setStatus(shoppingSessionRef.getStatus());
                    }
                    if (shoppingSessionRef.getCreatedDate() != null) {
                        existingShoppingSessionRef.setCreatedDate(shoppingSessionRef.getCreatedDate());
                    }
                    if (shoppingSessionRef.getSessionAbondoned() != null) {
                        existingShoppingSessionRef.setSessionAbondoned(shoppingSessionRef.getSessionAbondoned());
                    }
                    if (shoppingSessionRef.getCustomerId() != null) {
                        existingShoppingSessionRef.setCustomerId(shoppingSessionRef.getCustomerId());
                    }
                    if (shoppingSessionRef.getChannel() != null) {
                        existingShoppingSessionRef.setChannel(shoppingSessionRef.getChannel());
                    }
                    if (shoppingSessionRef.getIndividualId() != null) {
                        existingShoppingSessionRef.setIndividualId(shoppingSessionRef.getIndividualId());
                    }

                    return existingShoppingSessionRef;
                }
            )
            .map(shoppingSessionRefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShoppingSessionRef> findAll() {
        log.debug("Request to get all ShoppingSessionRefs");
        return shoppingSessionRefRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingSessionRef> findOne(Long id) {
        log.debug("Request to get ShoppingSessionRef : {}", id);
        return shoppingSessionRefRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingSessionRef : {}", id);
        shoppingSessionRefRepository.deleteById(id);
    }
}
