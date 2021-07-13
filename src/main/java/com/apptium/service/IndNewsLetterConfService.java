package com.apptium.service;

import com.apptium.domain.IndNewsLetterConf;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IndNewsLetterConf}.
 */
public interface IndNewsLetterConfService {
    /**
     * Save a indNewsLetterConf.
     *
     * @param indNewsLetterConf the entity to save.
     * @return the persisted entity.
     */
    IndNewsLetterConf save(IndNewsLetterConf indNewsLetterConf);

    /**
     * Partially updates a indNewsLetterConf.
     *
     * @param indNewsLetterConf the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndNewsLetterConf> partialUpdate(IndNewsLetterConf indNewsLetterConf);

    /**
     * Get all the indNewsLetterConfs.
     *
     * @return the list of entities.
     */
    List<IndNewsLetterConf> findAll();
    /**
     * Get all the IndNewsLetterConf where Individual is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<IndNewsLetterConf> findAllWhereIndividualIsNull();

    /**
     * Get the "id" indNewsLetterConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndNewsLetterConf> findOne(Long id);

    /**
     * Delete the "id" indNewsLetterConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
