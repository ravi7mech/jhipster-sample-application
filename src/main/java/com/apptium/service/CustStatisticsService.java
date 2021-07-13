package com.apptium.service;

import com.apptium.domain.CustStatistics;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustStatistics}.
 */
public interface CustStatisticsService {
    /**
     * Save a custStatistics.
     *
     * @param custStatistics the entity to save.
     * @return the persisted entity.
     */
    CustStatistics save(CustStatistics custStatistics);

    /**
     * Partially updates a custStatistics.
     *
     * @param custStatistics the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustStatistics> partialUpdate(CustStatistics custStatistics);

    /**
     * Get all the custStatistics.
     *
     * @return the list of entities.
     */
    List<CustStatistics> findAll();

    /**
     * Get the "id" custStatistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustStatistics> findOne(Long id);

    /**
     * Delete the "id" custStatistics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
