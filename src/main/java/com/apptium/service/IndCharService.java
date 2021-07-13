package com.apptium.service;

import com.apptium.domain.IndChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndChar}.
 */
public interface IndCharService {
    /**
     * Save a indChar.
     *
     * @param indChar the entity to save.
     * @return the persisted entity.
     */
    IndChar save(IndChar indChar);

    /**
     * Partially updates a indChar.
     *
     * @param indChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndChar> partialUpdate(IndChar indChar);

    /**
     * Get all the indChars.
     *
     * @return the list of entities.
     */
    List<IndChar> findAll();

    /**
     * Get the "id" indChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndChar> findOne(Long id);

    /**
     * Delete the "id" indChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
