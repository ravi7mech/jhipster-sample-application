package com.apptium.service;

import com.apptium.domain.Industry;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Industry}.
 */
public interface IndustryService {
    /**
     * Save a industry.
     *
     * @param industry the entity to save.
     * @return the persisted entity.
     */
    Industry save(Industry industry);

    /**
     * Partially updates a industry.
     *
     * @param industry the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Industry> partialUpdate(Industry industry);

    /**
     * Get all the industries.
     *
     * @return the list of entities.
     */
    List<Industry> findAll();

    /**
     * Get the "id" industry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Industry> findOne(Long id);

    /**
     * Delete the "id" industry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
