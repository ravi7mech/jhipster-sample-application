package com.apptium.service;

import com.apptium.domain.CustChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustChar}.
 */
public interface CustCharService {
    /**
     * Save a custChar.
     *
     * @param custChar the entity to save.
     * @return the persisted entity.
     */
    CustChar save(CustChar custChar);

    /**
     * Partially updates a custChar.
     *
     * @param custChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustChar> partialUpdate(CustChar custChar);

    /**
     * Get all the custChars.
     *
     * @return the list of entities.
     */
    List<CustChar> findAll();

    /**
     * Get the "id" custChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustChar> findOne(Long id);

    /**
     * Delete the "id" custChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
