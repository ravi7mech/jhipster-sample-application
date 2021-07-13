package com.apptium.web.rest;

import com.apptium.domain.IndAuditTrial;
import com.apptium.repository.IndAuditTrialRepository;
import com.apptium.service.IndAuditTrialService;
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
 * REST controller for managing {@link com.apptium.domain.IndAuditTrial}.
 */
@RestController
@RequestMapping("/api")
public class IndAuditTrialResource {

    private final Logger log = LoggerFactory.getLogger(IndAuditTrialResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndAuditTrial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndAuditTrialService indAuditTrialService;

    private final IndAuditTrialRepository indAuditTrialRepository;

    public IndAuditTrialResource(IndAuditTrialService indAuditTrialService, IndAuditTrialRepository indAuditTrialRepository) {
        this.indAuditTrialService = indAuditTrialService;
        this.indAuditTrialRepository = indAuditTrialRepository;
    }

    /**
     * {@code POST  /ind-audit-trials} : Create a new indAuditTrial.
     *
     * @param indAuditTrial the indAuditTrial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indAuditTrial, or with status {@code 400 (Bad Request)} if the indAuditTrial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-audit-trials")
    public ResponseEntity<IndAuditTrial> createIndAuditTrial(@Valid @RequestBody IndAuditTrial indAuditTrial) throws URISyntaxException {
        log.debug("REST request to save IndAuditTrial : {}", indAuditTrial);
        if (indAuditTrial.getId() != null) {
            throw new BadRequestAlertException("A new indAuditTrial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndAuditTrial result = indAuditTrialService.save(indAuditTrial);
        return ResponseEntity
            .created(new URI("/api/ind-audit-trials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-audit-trials/:id} : Updates an existing indAuditTrial.
     *
     * @param id the id of the indAuditTrial to save.
     * @param indAuditTrial the indAuditTrial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indAuditTrial,
     * or with status {@code 400 (Bad Request)} if the indAuditTrial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indAuditTrial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-audit-trials/{id}")
    public ResponseEntity<IndAuditTrial> updateIndAuditTrial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndAuditTrial indAuditTrial
    ) throws URISyntaxException {
        log.debug("REST request to update IndAuditTrial : {}, {}", id, indAuditTrial);
        if (indAuditTrial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indAuditTrial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indAuditTrialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndAuditTrial result = indAuditTrialService.save(indAuditTrial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indAuditTrial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-audit-trials/:id} : Partial updates given fields of an existing indAuditTrial, field will ignore if it is null
     *
     * @param id the id of the indAuditTrial to save.
     * @param indAuditTrial the indAuditTrial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indAuditTrial,
     * or with status {@code 400 (Bad Request)} if the indAuditTrial is not valid,
     * or with status {@code 404 (Not Found)} if the indAuditTrial is not found,
     * or with status {@code 500 (Internal Server Error)} if the indAuditTrial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-audit-trials/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndAuditTrial> partialUpdateIndAuditTrial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndAuditTrial indAuditTrial
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndAuditTrial partially : {}, {}", id, indAuditTrial);
        if (indAuditTrial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indAuditTrial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indAuditTrialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndAuditTrial> result = indAuditTrialService.partialUpdate(indAuditTrial);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indAuditTrial.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-audit-trials} : get all the indAuditTrials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indAuditTrials in body.
     */
    @GetMapping("/ind-audit-trials")
    public List<IndAuditTrial> getAllIndAuditTrials() {
        log.debug("REST request to get all IndAuditTrials");
        return indAuditTrialService.findAll();
    }

    /**
     * {@code GET  /ind-audit-trials/:id} : get the "id" indAuditTrial.
     *
     * @param id the id of the indAuditTrial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indAuditTrial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-audit-trials/{id}")
    public ResponseEntity<IndAuditTrial> getIndAuditTrial(@PathVariable Long id) {
        log.debug("REST request to get IndAuditTrial : {}", id);
        Optional<IndAuditTrial> indAuditTrial = indAuditTrialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indAuditTrial);
    }

    /**
     * {@code DELETE  /ind-audit-trials/:id} : delete the "id" indAuditTrial.
     *
     * @param id the id of the indAuditTrial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-audit-trials/{id}")
    public ResponseEntity<Void> deleteIndAuditTrial(@PathVariable Long id) {
        log.debug("REST request to delete IndAuditTrial : {}", id);
        indAuditTrialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
