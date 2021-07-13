package com.apptium.service;

import com.apptium.domain.CreditCheckVerification;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CreditCheckVerification}.
 */
public interface CreditCheckVerificationService {
    /**
     * Save a creditCheckVerification.
     *
     * @param creditCheckVerification the entity to save.
     * @return the persisted entity.
     */
    CreditCheckVerification save(CreditCheckVerification creditCheckVerification);

    /**
     * Partially updates a creditCheckVerification.
     *
     * @param creditCheckVerification the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditCheckVerification> partialUpdate(CreditCheckVerification creditCheckVerification);

    /**
     * Get all the creditCheckVerifications.
     *
     * @return the list of entities.
     */
    List<CreditCheckVerification> findAll();

    /**
     * Get the "id" creditCheckVerification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCheckVerification> findOne(Long id);

    /**
     * Delete the "id" creditCheckVerification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
