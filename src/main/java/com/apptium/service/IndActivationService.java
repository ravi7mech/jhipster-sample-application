package com.apptium.service;

import com.apptium.domain.IndActivation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndActivation}.
 */
public interface IndActivationService {
    /**
     * Save a indActivation.
     *
     * @param indActivation the entity to save.
     * @return the persisted entity.
     */
    IndActivation save(IndActivation indActivation);

    /**
     * Partially updates a indActivation.
     *
     * @param indActivation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndActivation> partialUpdate(IndActivation indActivation);

    /**
     * Get all the indActivations.
     *
     * @return the list of entities.
     */
    List<IndActivation> findAll();
    /**
     * Get all the IndActivation where Individual is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<IndActivation> findAllWhereIndividualIsNull();

    /**
     * Get the "id" indActivation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndActivation> findOne(Long id);

    /**
     * Delete the "id" indActivation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
