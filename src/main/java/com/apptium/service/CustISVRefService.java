package com.apptium.service;

import com.apptium.domain.CustISVRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustISVRef}.
 */
public interface CustISVRefService {
    /**
     * Save a custISVRef.
     *
     * @param custISVRef the entity to save.
     * @return the persisted entity.
     */
    CustISVRef save(CustISVRef custISVRef);

    /**
     * Partially updates a custISVRef.
     *
     * @param custISVRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustISVRef> partialUpdate(CustISVRef custISVRef);

    /**
     * Get all the custISVRefs.
     *
     * @return the list of entities.
     */
    List<CustISVRef> findAll();

    /**
     * Get the "id" custISVRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustISVRef> findOne(Long id);

    /**
     * Delete the "id" custISVRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
