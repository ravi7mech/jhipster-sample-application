package com.apptium.service;

import com.apptium.domain.ShoppingSessionRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ShoppingSessionRef}.
 */
public interface ShoppingSessionRefService {
    /**
     * Save a shoppingSessionRef.
     *
     * @param shoppingSessionRef the entity to save.
     * @return the persisted entity.
     */
    ShoppingSessionRef save(ShoppingSessionRef shoppingSessionRef);

    /**
     * Partially updates a shoppingSessionRef.
     *
     * @param shoppingSessionRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoppingSessionRef> partialUpdate(ShoppingSessionRef shoppingSessionRef);

    /**
     * Get all the shoppingSessionRefs.
     *
     * @return the list of entities.
     */
    List<ShoppingSessionRef> findAll();

    /**
     * Get the "id" shoppingSessionRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingSessionRef> findOne(Long id);

    /**
     * Delete the "id" shoppingSessionRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
