package com.apptium.web.rest;

import com.apptium.domain.IndActivation;
import com.apptium.repository.IndActivationRepository;
import com.apptium.service.IndActivationService;
import com.apptium.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.apptium.domain.IndActivation}.
 */
@RestController
@RequestMapping("/api")
public class IndActivationResource {

    private final Logger log = LoggerFactory.getLogger(IndActivationResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndActivation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndActivationService indActivationService;

    private final IndActivationRepository indActivationRepository;

    public IndActivationResource(IndActivationService indActivationService, IndActivationRepository indActivationRepository) {
        this.indActivationService = indActivationService;
        this.indActivationRepository = indActivationRepository;
    }

    /**
     * {@code POST  /ind-activations} : Create a new indActivation.
     *
     * @param indActivation the indActivation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indActivation, or with status {@code 400 (Bad Request)} if the indActivation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-activations")
    public ResponseEntity<IndActivation> createIndActivation(@Valid @RequestBody IndActivation indActivation) throws URISyntaxException {
        log.debug("REST request to save IndActivation : {}", indActivation);
        if (indActivation.getId() != null) {
            throw new BadRequestAlertException("A new indActivation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndActivation result = indActivationService.save(indActivation);
        return ResponseEntity
            .created(new URI("/api/ind-activations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-activations/:id} : Updates an existing indActivation.
     *
     * @param id the id of the indActivation to save.
     * @param indActivation the indActivation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indActivation,
     * or with status {@code 400 (Bad Request)} if the indActivation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indActivation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-activations/{id}")
    public ResponseEntity<IndActivation> updateIndActivation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndActivation indActivation
    ) throws URISyntaxException {
        log.debug("REST request to update IndActivation : {}, {}", id, indActivation);
        if (indActivation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indActivation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indActivationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndActivation result = indActivationService.save(indActivation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indActivation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-activations/:id} : Partial updates given fields of an existing indActivation, field will ignore if it is null
     *
     * @param id the id of the indActivation to save.
     * @param indActivation the indActivation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indActivation,
     * or with status {@code 400 (Bad Request)} if the indActivation is not valid,
     * or with status {@code 404 (Not Found)} if the indActivation is not found,
     * or with status {@code 500 (Internal Server Error)} if the indActivation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-activations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndActivation> partialUpdateIndActivation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndActivation indActivation
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndActivation partially : {}, {}", id, indActivation);
        if (indActivation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indActivation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indActivationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndActivation> result = indActivationService.partialUpdate(indActivation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indActivation.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-activations} : get all the indActivations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indActivations in body.
     */
    @GetMapping("/ind-activations")
    public List<IndActivation> getAllIndActivations(@RequestParam(required = false) String filter) {
        if ("individual-is-null".equals(filter)) {
            log.debug("REST request to get all IndActivations where individual is null");
            return indActivationService.findAllWhereIndividualIsNull();
        }
        log.debug("REST request to get all IndActivations");
        return indActivationService.findAll();
    }

    /**
     * {@code GET  /ind-activations/:id} : get the "id" indActivation.
     *
     * @param id the id of the indActivation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indActivation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-activations/{id}")
    public ResponseEntity<IndActivation> getIndActivation(@PathVariable Long id) {
        log.debug("REST request to get IndActivation : {}", id);
        Optional<IndActivation> indActivation = indActivationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indActivation);
    }

    /**
     * {@code DELETE  /ind-activations/:id} : delete the "id" indActivation.
     *
     * @param id the id of the indActivation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-activations/{id}")
    public ResponseEntity<Void> deleteIndActivation(@PathVariable Long id) {
        log.debug("REST request to delete IndActivation : {}", id);
        indActivationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
