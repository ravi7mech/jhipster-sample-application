package com.apptium.web.rest;

import com.apptium.domain.Eligibility;
import com.apptium.repository.EligibilityRepository;
import com.apptium.service.EligibilityService;
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
 * REST controller for managing {@link com.apptium.domain.Eligibility}.
 */
@RestController
@RequestMapping("/api")
public class EligibilityResource {

    private final Logger log = LoggerFactory.getLogger(EligibilityResource.class);

    private static final String ENTITY_NAME = "orderMgmtEligibility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibilityService eligibilityService;

    private final EligibilityRepository eligibilityRepository;

    public EligibilityResource(EligibilityService eligibilityService, EligibilityRepository eligibilityRepository) {
        this.eligibilityService = eligibilityService;
        this.eligibilityRepository = eligibilityRepository;
    }

    /**
     * {@code POST  /eligibilities} : Create a new eligibility.
     *
     * @param eligibility the eligibility to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibility, or with status {@code 400 (Bad Request)} if the eligibility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibilities")
    public ResponseEntity<Eligibility> createEligibility(@Valid @RequestBody Eligibility eligibility) throws URISyntaxException {
        log.debug("REST request to save Eligibility : {}", eligibility);
        if (eligibility.getId() != null) {
            throw new BadRequestAlertException("A new eligibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eligibility result = eligibilityService.save(eligibility);
        return ResponseEntity
            .created(new URI("/api/eligibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilities/:id} : Updates an existing eligibility.
     *
     * @param id the id of the eligibility to save.
     * @param eligibility the eligibility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibility,
     * or with status {@code 400 (Bad Request)} if the eligibility is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibilities/{id}")
    public ResponseEntity<Eligibility> updateEligibility(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Eligibility eligibility
    ) throws URISyntaxException {
        log.debug("REST request to update Eligibility : {}, {}", id, eligibility);
        if (eligibility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibility.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Eligibility result = eligibilityService.save(eligibility);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibility.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eligibilities/:id} : Partial updates given fields of an existing eligibility, field will ignore if it is null
     *
     * @param id the id of the eligibility to save.
     * @param eligibility the eligibility to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibility,
     * or with status {@code 400 (Bad Request)} if the eligibility is not valid,
     * or with status {@code 404 (Not Found)} if the eligibility is not found,
     * or with status {@code 500 (Internal Server Error)} if the eligibility couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eligibilities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Eligibility> partialUpdateEligibility(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Eligibility eligibility
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eligibility partially : {}, {}", id, eligibility);
        if (eligibility.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibility.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eligibility> result = eligibilityService.partialUpdate(eligibility);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibility.getId().toString())
        );
    }

    /**
     * {@code GET  /eligibilities} : get all the eligibilities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilities in body.
     */
    @GetMapping("/eligibilities")
    public List<Eligibility> getAllEligibilities() {
        log.debug("REST request to get all Eligibilities");
        return eligibilityService.findAll();
    }

    /**
     * {@code GET  /eligibilities/:id} : get the "id" eligibility.
     *
     * @param id the id of the eligibility to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibility, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibilities/{id}")
    public ResponseEntity<Eligibility> getEligibility(@PathVariable Long id) {
        log.debug("REST request to get Eligibility : {}", id);
        Optional<Eligibility> eligibility = eligibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibility);
    }

    /**
     * {@code DELETE  /eligibilities/:id} : delete the "id" eligibility.
     *
     * @param id the id of the eligibility to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibilities/{id}")
    public ResponseEntity<Void> deleteEligibility(@PathVariable Long id) {
        log.debug("REST request to delete Eligibility : {}", id);
        eligibilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
