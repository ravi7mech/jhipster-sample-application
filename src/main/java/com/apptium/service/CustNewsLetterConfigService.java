package com.apptium.service;

import com.apptium.domain.CustNewsLetterConfig;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustNewsLetterConfig}.
 */
public interface CustNewsLetterConfigService {
    /**
     * Save a custNewsLetterConfig.
     *
     * @param custNewsLetterConfig the entity to save.
     * @return the persisted entity.
     */
    CustNewsLetterConfig save(CustNewsLetterConfig custNewsLetterConfig);

    /**
     * Partially updates a custNewsLetterConfig.
     *
     * @param custNewsLetterConfig the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustNewsLetterConfig> partialUpdate(CustNewsLetterConfig custNewsLetterConfig);

    /**
     * Get all the custNewsLetterConfigs.
     *
     * @return the list of entities.
     */
    List<CustNewsLetterConfig> findAll();

    /**
     * Get the "id" custNewsLetterConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustNewsLetterConfig> findOne(Long id);

    /**
     * Delete the "id" custNewsLetterConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
