package com.apptium.service;

import com.apptium.domain.IndContact;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndContact}.
 */
public interface IndContactService {
    /**
     * Save a indContact.
     *
     * @param indContact the entity to save.
     * @return the persisted entity.
     */
    IndContact save(IndContact indContact);

    /**
     * Partially updates a indContact.
     *
     * @param indContact the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndContact> partialUpdate(IndContact indContact);

    /**
     * Get all the indContacts.
     *
     * @return the list of entities.
     */
    List<IndContact> findAll();

    /**
     * Get the "id" indContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndContact> findOne(Long id);

    /**
     * Delete the "id" indContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
