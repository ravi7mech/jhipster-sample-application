package com.apptium.service;

import com.apptium.domain.NewsLetterType;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NewsLetterType}.
 */
public interface NewsLetterTypeService {
    /**
     * Save a newsLetterType.
     *
     * @param newsLetterType the entity to save.
     * @return the persisted entity.
     */
    NewsLetterType save(NewsLetterType newsLetterType);

    /**
     * Partially updates a newsLetterType.
     *
     * @param newsLetterType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NewsLetterType> partialUpdate(NewsLetterType newsLetterType);

    /**
     * Get all the newsLetterTypes.
     *
     * @return the list of entities.
     */
    List<NewsLetterType> findAll();

    /**
     * Get the "id" newsLetterType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewsLetterType> findOne(Long id);

    /**
     * Delete the "id" newsLetterType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
