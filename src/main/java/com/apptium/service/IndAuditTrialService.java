package com.apptium.service;

import com.apptium.domain.IndAuditTrial;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndAuditTrial}.
 */
public interface IndAuditTrialService {
    /**
     * Save a indAuditTrial.
     *
     * @param indAuditTrial the entity to save.
     * @return the persisted entity.
     */
    IndAuditTrial save(IndAuditTrial indAuditTrial);

    /**
     * Partially updates a indAuditTrial.
     *
     * @param indAuditTrial the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndAuditTrial> partialUpdate(IndAuditTrial indAuditTrial);

    /**
     * Get all the indAuditTrials.
     *
     * @return the list of entities.
     */
    List<IndAuditTrial> findAll();

    /**
     * Get the "id" indAuditTrial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndAuditTrial> findOne(Long id);

    /**
     * Delete the "id" indAuditTrial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
