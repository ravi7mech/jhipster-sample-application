package com.apptium.service;

import com.apptium.domain.CustContact;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustContact}.
 */
public interface CustContactService {
    /**
     * Save a custContact.
     *
     * @param custContact the entity to save.
     * @return the persisted entity.
     */
    CustContact save(CustContact custContact);

    /**
     * Partially updates a custContact.
     *
     * @param custContact the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustContact> partialUpdate(CustContact custContact);

    /**
     * Get all the custContacts.
     *
     * @return the list of entities.
     */
    List<CustContact> findAll();

    /**
     * Get the "id" custContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustContact> findOne(Long id);

    /**
     * Delete the "id" custContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
