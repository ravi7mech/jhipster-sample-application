package com.apptium.service;

import com.apptium.domain.CustISVChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustISVChar}.
 */
public interface CustISVCharService {
    /**
     * Save a custISVChar.
     *
     * @param custISVChar the entity to save.
     * @return the persisted entity.
     */
    CustISVChar save(CustISVChar custISVChar);

    /**
     * Partially updates a custISVChar.
     *
     * @param custISVChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustISVChar> partialUpdate(CustISVChar custISVChar);

    /**
     * Get all the custISVChars.
     *
     * @return the list of entities.
     */
    List<CustISVChar> findAll();

    /**
     * Get the "id" custISVChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustISVChar> findOne(Long id);

    /**
     * Delete the "id" custISVChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
