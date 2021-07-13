package com.apptium.service;

import com.apptium.domain.GeographicSiteRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GeographicSiteRef}.
 */
public interface GeographicSiteRefService {
    /**
     * Save a geographicSiteRef.
     *
     * @param geographicSiteRef the entity to save.
     * @return the persisted entity.
     */
    GeographicSiteRef save(GeographicSiteRef geographicSiteRef);

    /**
     * Partially updates a geographicSiteRef.
     *
     * @param geographicSiteRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GeographicSiteRef> partialUpdate(GeographicSiteRef geographicSiteRef);

    /**
     * Get all the geographicSiteRefs.
     *
     * @return the list of entities.
     */
    List<GeographicSiteRef> findAll();

    /**
     * Get the "id" geographicSiteRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GeographicSiteRef> findOne(Long id);

    /**
     * Delete the "id" geographicSiteRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
