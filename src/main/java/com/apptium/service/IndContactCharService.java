package com.apptium.service;

import com.apptium.domain.IndContactChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndContactChar}.
 */
public interface IndContactCharService {
    /**
     * Save a indContactChar.
     *
     * @param indContactChar the entity to save.
     * @return the persisted entity.
     */
    IndContactChar save(IndContactChar indContactChar);

    /**
     * Partially updates a indContactChar.
     *
     * @param indContactChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndContactChar> partialUpdate(IndContactChar indContactChar);

    /**
     * Get all the indContactChars.
     *
     * @return the list of entities.
     */
    List<IndContactChar> findAll();

    /**
     * Get the "id" indContactChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndContactChar> findOne(Long id);

    /**
     * Delete the "id" indContactChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
