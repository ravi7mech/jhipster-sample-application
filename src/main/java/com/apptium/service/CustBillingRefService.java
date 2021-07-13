package com.apptium.service;

import com.apptium.domain.CustBillingRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustBillingRef}.
 */
public interface CustBillingRefService {
    /**
     * Save a custBillingRef.
     *
     * @param custBillingRef the entity to save.
     * @return the persisted entity.
     */
    CustBillingRef save(CustBillingRef custBillingRef);

    /**
     * Partially updates a custBillingRef.
     *
     * @param custBillingRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustBillingRef> partialUpdate(CustBillingRef custBillingRef);

    /**
     * Get all the custBillingRefs.
     *
     * @return the list of entities.
     */
    List<CustBillingRef> findAll();
    /**
     * Get all the CustBillingRef where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustBillingRef> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custBillingRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustBillingRef> findOne(Long id);

    /**
     * Delete the "id" custBillingRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
