package com.apptium.service;

import com.apptium.domain.CustPasswordChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustPasswordChar}.
 */
public interface CustPasswordCharService {
    /**
     * Save a custPasswordChar.
     *
     * @param custPasswordChar the entity to save.
     * @return the persisted entity.
     */
    CustPasswordChar save(CustPasswordChar custPasswordChar);

    /**
     * Partially updates a custPasswordChar.
     *
     * @param custPasswordChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustPasswordChar> partialUpdate(CustPasswordChar custPasswordChar);

    /**
     * Get all the custPasswordChars.
     *
     * @return the list of entities.
     */
    List<CustPasswordChar> findAll();

    /**
     * Get the "id" custPasswordChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustPasswordChar> findOne(Long id);

    /**
     * Delete the "id" custPasswordChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
