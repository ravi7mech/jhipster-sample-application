package com.apptium.service;

import com.apptium.domain.BankCardType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BankCardType}.
 */
public interface BankCardTypeService {
    /**
     * Save a bankCardType.
     *
     * @param bankCardType the entity to save.
     * @return the persisted entity.
     */
    BankCardType save(BankCardType bankCardType);

    /**
     * Partially updates a bankCardType.
     *
     * @param bankCardType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankCardType> partialUpdate(BankCardType bankCardType);

    /**
     * Get all the bankCardTypes.
     *
     * @return the list of entities.
     */
    List<BankCardType> findAll();

    /**
     * Get the "id" bankCardType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankCardType> findOne(Long id);

    /**
     * Delete the "id" bankCardType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
