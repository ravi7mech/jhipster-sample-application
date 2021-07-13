package com.apptium.service;

import com.apptium.domain.CustSecurityChar;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustSecurityChar}.
 */
public interface CustSecurityCharService {
    /**
     * Save a custSecurityChar.
     *
     * @param custSecurityChar the entity to save.
     * @return the persisted entity.
     */
    CustSecurityChar save(CustSecurityChar custSecurityChar);

    /**
     * Partially updates a custSecurityChar.
     *
     * @param custSecurityChar the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustSecurityChar> partialUpdate(CustSecurityChar custSecurityChar);

    /**
     * Get all the custSecurityChars.
     *
     * @return the list of entities.
     */
    List<CustSecurityChar> findAll();

    /**
     * Get the "id" custSecurityChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustSecurityChar> findOne(Long id);

    /**
     * Delete the "id" custSecurityChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
