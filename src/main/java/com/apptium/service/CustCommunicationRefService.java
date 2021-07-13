package com.apptium.service;

import com.apptium.domain.CustCommunicationRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustCommunicationRef}.
 */
public interface CustCommunicationRefService {
    /**
     * Save a custCommunicationRef.
     *
     * @param custCommunicationRef the entity to save.
     * @return the persisted entity.
     */
    CustCommunicationRef save(CustCommunicationRef custCommunicationRef);

    /**
     * Partially updates a custCommunicationRef.
     *
     * @param custCommunicationRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustCommunicationRef> partialUpdate(CustCommunicationRef custCommunicationRef);

    /**
     * Get all the custCommunicationRefs.
     *
     * @return the list of entities.
     */
    List<CustCommunicationRef> findAll();

    /**
     * Get the "id" custCommunicationRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustCommunicationRef> findOne(Long id);

    /**
     * Delete the "id" custCommunicationRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
