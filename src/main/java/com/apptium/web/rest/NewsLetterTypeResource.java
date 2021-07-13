package com.apptium.web.rest;

import com.apptium.domain.NewsLetterType;
import com.apptium.repository.NewsLetterTypeRepository;
import com.apptium.service.NewsLetterTypeService;
import com.apptium.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.domain.NewsLetterType}.
 */
@RestController
@RequestMapping("/api")
public class NewsLetterTypeResource {

    private final Logger log = LoggerFactory.getLogger(NewsLetterTypeResource.class);

    private static final String ENTITY_NAME = "orderMgmtNewsLetterType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsLetterTypeService newsLetterTypeService;

    private final NewsLetterTypeRepository newsLetterTypeRepository;

    public NewsLetterTypeResource(NewsLetterTypeService newsLetterTypeService, NewsLetterTypeRepository newsLetterTypeRepository) {
        this.newsLetterTypeService = newsLetterTypeService;
        this.newsLetterTypeRepository = newsLetterTypeRepository;
    }

    /**
     * {@code POST  /news-letter-types} : Create a new newsLetterType.
     *
     * @param newsLetterType the newsLetterType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsLetterType, or with status {@code 400 (Bad Request)} if the newsLetterType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-letter-types")
    public ResponseEntity<NewsLetterType> createNewsLetterType(@Valid @RequestBody NewsLetterType newsLetterType)
        throws URISyntaxException {
        log.debug("REST request to save NewsLetterType : {}", newsLetterType);
        if (newsLetterType.getId() != null) {
            throw new BadRequestAlertException("A new newsLetterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsLetterType result = newsLetterTypeService.save(newsLetterType);
        return ResponseEntity
            .created(new URI("/api/news-letter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-letter-types/:id} : Updates an existing newsLetterType.
     *
     * @param id the id of the newsLetterType to save.
     * @param newsLetterType the newsLetterType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsLetterType,
     * or with status {@code 400 (Bad Request)} if the newsLetterType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsLetterType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-letter-types/{id}")
    public ResponseEntity<NewsLetterType> updateNewsLetterType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NewsLetterType newsLetterType
    ) throws URISyntaxException {
        log.debug("REST request to update NewsLetterType : {}, {}", id, newsLetterType);
        if (newsLetterType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsLetterType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsLetterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NewsLetterType result = newsLetterTypeService.save(newsLetterType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsLetterType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /news-letter-types/:id} : Partial updates given fields of an existing newsLetterType, field will ignore if it is null
     *
     * @param id the id of the newsLetterType to save.
     * @param newsLetterType the newsLetterType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsLetterType,
     * or with status {@code 400 (Bad Request)} if the newsLetterType is not valid,
     * or with status {@code 404 (Not Found)} if the newsLetterType is not found,
     * or with status {@code 500 (Internal Server Error)} if the newsLetterType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/news-letter-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NewsLetterType> partialUpdateNewsLetterType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NewsLetterType newsLetterType
    ) throws URISyntaxException {
        log.debug("REST request to partial update NewsLetterType partially : {}, {}", id, newsLetterType);
        if (newsLetterType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsLetterType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsLetterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NewsLetterType> result = newsLetterTypeService.partialUpdate(newsLetterType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsLetterType.getId().toString())
        );
    }

    /**
     * {@code GET  /news-letter-types} : get all the newsLetterTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsLetterTypes in body.
     */
    @GetMapping("/news-letter-types")
    public List<NewsLetterType> getAllNewsLetterTypes() {
        log.debug("REST request to get all NewsLetterTypes");
        return newsLetterTypeService.findAll();
    }

    /**
     * {@code GET  /news-letter-types/:id} : get the "id" newsLetterType.
     *
     * @param id the id of the newsLetterType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsLetterType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-letter-types/{id}")
    public ResponseEntity<NewsLetterType> getNewsLetterType(@PathVariable Long id) {
        log.debug("REST request to get NewsLetterType : {}", id);
        Optional<NewsLetterType> newsLetterType = newsLetterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsLetterType);
    }

    /**
     * {@code DELETE  /news-letter-types/:id} : delete the "id" newsLetterType.
     *
     * @param id the id of the newsLetterType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-letter-types/{id}")
    public ResponseEntity<Void> deleteNewsLetterType(@PathVariable Long id) {
        log.debug("REST request to delete NewsLetterType : {}", id);
        newsLetterTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
