package com.apptium.service;

import com.apptium.domain.RoleTypeRef;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RoleTypeRef}.
 */
public interface RoleTypeRefService {
    /**
     * Save a roleTypeRef.
     *
     * @param roleTypeRef the entity to save.
     * @return the persisted entity.
     */
    RoleTypeRef save(RoleTypeRef roleTypeRef);

    /**
     * Partially updates a roleTypeRef.
     *
     * @param roleTypeRef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleTypeRef> partialUpdate(RoleTypeRef roleTypeRef);

    /**
     * Get all the roleTypeRefs.
     *
     * @return the list of entities.
     */
    List<RoleTypeRef> findAll();

    /**
     * Get the "id" roleTypeRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleTypeRef> findOne(Long id);

    /**
     * Delete the "id" roleTypeRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
