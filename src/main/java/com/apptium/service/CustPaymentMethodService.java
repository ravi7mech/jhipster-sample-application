package com.apptium.service;

import com.apptium.domain.CustPaymentMethod;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustPaymentMethod}.
 */
public interface CustPaymentMethodService {
    /**
     * Save a custPaymentMethod.
     *
     * @param custPaymentMethod the entity to save.
     * @return the persisted entity.
     */
    CustPaymentMethod save(CustPaymentMethod custPaymentMethod);

    /**
     * Partially updates a custPaymentMethod.
     *
     * @param custPaymentMethod the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustPaymentMethod> partialUpdate(CustPaymentMethod custPaymentMethod);

    /**
     * Get all the custPaymentMethods.
     *
     * @return the list of entities.
     */
    List<CustPaymentMethod> findAll();

    /**
     * Get the "id" custPaymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustPaymentMethod> findOne(Long id);

    /**
     * Delete the "id" custPaymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
