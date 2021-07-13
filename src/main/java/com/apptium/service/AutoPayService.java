package com.apptium.service;

import com.apptium.domain.AutoPay;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link AutoPay}.
 */
public interface AutoPayService {
    /**
     * Save a autoPay.
     *
     * @param autoPay the entity to save.
     * @return the persisted entity.
     */
    AutoPay save(AutoPay autoPay);

    /**
     * Partially updates a autoPay.
     *
     * @param autoPay the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AutoPay> partialUpdate(AutoPay autoPay);

    /**
     * Get all the autoPays.
     *
     * @return the list of entities.
     */
    List<AutoPay> findAll();

    /**
     * Get the "id" autoPay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AutoPay> findOne(Long id);

    /**
     * Delete the "id" autoPay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
