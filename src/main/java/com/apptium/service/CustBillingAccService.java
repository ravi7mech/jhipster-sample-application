package com.apptium.service;

import com.apptium.domain.CustBillingAcc;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustBillingAcc}.
 */
public interface CustBillingAccService {
    /**
     * Save a custBillingAcc.
     *
     * @param custBillingAcc the entity to save.
     * @return the persisted entity.
     */
    CustBillingAcc save(CustBillingAcc custBillingAcc);

    /**
     * Partially updates a custBillingAcc.
     *
     * @param custBillingAcc the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustBillingAcc> partialUpdate(CustBillingAcc custBillingAcc);

    /**
     * Get all the custBillingAccs.
     *
     * @return the list of entities.
     */
    List<CustBillingAcc> findAll();
    /**
     * Get all the CustBillingAcc where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustBillingAcc> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custBillingAcc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustBillingAcc> findOne(Long id);

    /**
     * Delete the "id" custBillingAcc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
