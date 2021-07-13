package com.apptium.service;

import com.apptium.domain.Eligibility;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Eligibility}.
 */
public interface EligibilityService {
    /**
     * Save a eligibility.
     *
     * @param eligibility the entity to save.
     * @return the persisted entity.
     */
    Eligibility save(Eligibility eligibility);

    /**
     * Partially updates a eligibility.
     *
     * @param eligibility the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Eligibility> partialUpdate(Eligibility eligibility);

    /**
     * Get all the eligibilities.
     *
     * @return the list of entities.
     */
    List<Eligibility> findAll();

    /**
     * Get the "id" eligibility.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Eligibility> findOne(Long id);

    /**
     * Delete the "id" eligibility.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
