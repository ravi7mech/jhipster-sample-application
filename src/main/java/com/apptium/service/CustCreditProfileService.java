package com.apptium.service;

import com.apptium.domain.CustCreditProfile;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustCreditProfile}.
 */
public interface CustCreditProfileService {
    /**
     * Save a custCreditProfile.
     *
     * @param custCreditProfile the entity to save.
     * @return the persisted entity.
     */
    CustCreditProfile save(CustCreditProfile custCreditProfile);

    /**
     * Partially updates a custCreditProfile.
     *
     * @param custCreditProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustCreditProfile> partialUpdate(CustCreditProfile custCreditProfile);

    /**
     * Get all the custCreditProfiles.
     *
     * @return the list of entities.
     */
    List<CustCreditProfile> findAll();
    /**
     * Get all the CustCreditProfile where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustCreditProfile> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custCreditProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustCreditProfile> findOne(Long id);

    /**
     * Delete the "id" custCreditProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
