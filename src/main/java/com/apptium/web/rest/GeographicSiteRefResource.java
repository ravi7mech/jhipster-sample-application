package com.apptium.web.rest;

import com.apptium.domain.GeographicSiteRef;
import com.apptium.repository.GeographicSiteRefRepository;
import com.apptium.service.GeographicSiteRefService;
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
 * REST controller for managing {@link com.apptium.domain.GeographicSiteRef}.
 */
@RestController
@RequestMapping("/api")
public class GeographicSiteRefResource {

    private final Logger log = LoggerFactory.getLogger(GeographicSiteRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtGeographicSiteRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeographicSiteRefService geographicSiteRefService;

    private final GeographicSiteRefRepository geographicSiteRefRepository;

    public GeographicSiteRefResource(
        GeographicSiteRefService geographicSiteRefService,
        GeographicSiteRefRepository geographicSiteRefRepository
    ) {
        this.geographicSiteRefService = geographicSiteRefService;
        this.geographicSiteRefRepository = geographicSiteRefRepository;
    }

    /**
     * {@code POST  /geographic-site-refs} : Create a new geographicSiteRef.
     *
     * @param geographicSiteRef the geographicSiteRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geographicSiteRef, or with status {@code 400 (Bad Request)} if the geographicSiteRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geographic-site-refs")
    public ResponseEntity<GeographicSiteRef> createGeographicSiteRef(@Valid @RequestBody GeographicSiteRef geographicSiteRef)
        throws URISyntaxException {
        log.debug("REST request to save GeographicSiteRef : {}", geographicSiteRef);
        if (geographicSiteRef.getId() != null) {
            throw new BadRequestAlertException("A new geographicSiteRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicSiteRef result = geographicSiteRefService.save(geographicSiteRef);
        return ResponseEntity
            .created(new URI("/api/geographic-site-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geographic-site-refs/:id} : Updates an existing geographicSiteRef.
     *
     * @param id the id of the geographicSiteRef to save.
     * @param geographicSiteRef the geographicSiteRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicSiteRef,
     * or with status {@code 400 (Bad Request)} if the geographicSiteRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geographicSiteRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geographic-site-refs/{id}")
    public ResponseEntity<GeographicSiteRef> updateGeographicSiteRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GeographicSiteRef geographicSiteRef
    ) throws URISyntaxException {
        log.debug("REST request to update GeographicSiteRef : {}, {}", id, geographicSiteRef);
        if (geographicSiteRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geographicSiteRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geographicSiteRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GeographicSiteRef result = geographicSiteRefService.save(geographicSiteRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geographicSiteRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /geographic-site-refs/:id} : Partial updates given fields of an existing geographicSiteRef, field will ignore if it is null
     *
     * @param id the id of the geographicSiteRef to save.
     * @param geographicSiteRef the geographicSiteRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicSiteRef,
     * or with status {@code 400 (Bad Request)} if the geographicSiteRef is not valid,
     * or with status {@code 404 (Not Found)} if the geographicSiteRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the geographicSiteRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/geographic-site-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GeographicSiteRef> partialUpdateGeographicSiteRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GeographicSiteRef geographicSiteRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update GeographicSiteRef partially : {}, {}", id, geographicSiteRef);
        if (geographicSiteRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geographicSiteRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geographicSiteRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GeographicSiteRef> result = geographicSiteRefService.partialUpdate(geographicSiteRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geographicSiteRef.getId().toString())
        );
    }

    /**
     * {@code GET  /geographic-site-refs} : get all the geographicSiteRefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geographicSiteRefs in body.
     */
    @GetMapping("/geographic-site-refs")
    public List<GeographicSiteRef> getAllGeographicSiteRefs() {
        log.debug("REST request to get all GeographicSiteRefs");
        return geographicSiteRefService.findAll();
    }

    /**
     * {@code GET  /geographic-site-refs/:id} : get the "id" geographicSiteRef.
     *
     * @param id the id of the geographicSiteRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geographicSiteRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geographic-site-refs/{id}")
    public ResponseEntity<GeographicSiteRef> getGeographicSiteRef(@PathVariable Long id) {
        log.debug("REST request to get GeographicSiteRef : {}", id);
        Optional<GeographicSiteRef> geographicSiteRef = geographicSiteRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicSiteRef);
    }

    /**
     * {@code DELETE  /geographic-site-refs/:id} : delete the "id" geographicSiteRef.
     *
     * @param id the id of the geographicSiteRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geographic-site-refs/{id}")
    public ResponseEntity<Void> deleteGeographicSiteRef(@PathVariable Long id) {
        log.debug("REST request to delete GeographicSiteRef : {}", id);
        geographicSiteRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
