package com.apptium.service;

import com.apptium.domain.CustContactChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustContactChar}.
 */
public interface CustContactCharService {
    /**
     * Save a custContactChar.
     *
     * @param custContactChar the entity to save.
     * @return the persisted entity.
     */
    CustContactChar save(CustContactChar custContactChar);

    /**
     * Partially updates a custContactChar.
     *
     * @param custContactChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustContactChar> partialUpdate(CustContactChar custContactChar);

    /**
     * Get all the custContactChars.
     *
     * @return the list of entities.
     */
    List<CustContactChar> findAll();

    /**
     * Get the "id" custContactChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustContactChar> findOne(Long id);

    /**
     * Delete the "id" custContactChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
