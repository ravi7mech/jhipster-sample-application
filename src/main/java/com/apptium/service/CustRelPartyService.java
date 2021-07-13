package com.apptium.service;

import com.apptium.domain.CustRelParty;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustRelParty}.
 */
public interface CustRelPartyService {
    /**
     * Save a custRelParty.
     *
     * @param custRelParty the entity to save.
     * @return the persisted entity.
     */
    CustRelParty save(CustRelParty custRelParty);

    /**
     * Partially updates a custRelParty.
     *
     * @param custRelParty the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustRelParty> partialUpdate(CustRelParty custRelParty);

    /**
     * Get all the custRelParties.
     *
     * @return the list of entities.
     */
    List<CustRelParty> findAll();

    /**
     * Get the "id" custRelParty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustRelParty> findOne(Long id);

    /**
     * Delete the "id" custRelParty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
