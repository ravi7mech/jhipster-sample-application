package com.apptium.web.rest;

import com.apptium.domain.CreditCheckVerification;
import com.apptium.repository.CreditCheckVerificationRepository;
import com.apptium.service.CreditCheckVerificationService;
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
 * REST controller for managing {@link com.apptium.domain.CreditCheckVerification}.
 */
@RestController
@RequestMapping("/api")
public class CreditCheckVerificationResource {

    private final Logger log = LoggerFactory.getLogger(CreditCheckVerificationResource.class);

    private static final String ENTITY_NAME = "orderMgmtCreditCheckVerification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCheckVerificationService creditCheckVerificationService;

    private final CreditCheckVerificationRepository creditCheckVerificationRepository;

    public CreditCheckVerificationResource(
        CreditCheckVerificationService creditCheckVerificationService,
        CreditCheckVerificationRepository creditCheckVerificationRepository
    ) {
        this.creditCheckVerificationService = creditCheckVerificationService;
        this.creditCheckVerificationRepository = creditCheckVerificationRepository;
    }

    /**
     * {@code POST  /credit-check-verifications} : Create a new creditCheckVerification.
     *
     * @param creditCheckVerification the creditCheckVerification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCheckVerification, or with status {@code 400 (Bad Request)} if the creditCheckVerification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-check-verifications")
    public ResponseEntity<CreditCheckVerification> createCreditCheckVerification(
        @Valid @RequestBody CreditCheckVerification creditCheckVerification
    ) throws URISyntaxException {
        log.debug("REST request to save CreditCheckVerification : {}", creditCheckVerification);
        if (creditCheckVerification.getId() != null) {
            throw new BadRequestAlertException("A new creditCheckVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCheckVerification result = creditCheckVerificationService.save(creditCheckVerification);
        return ResponseEntity
            .created(new URI("/api/credit-check-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-check-verifications/:id} : Updates an existing creditCheckVerification.
     *
     * @param id the id of the creditCheckVerification to save.
     * @param creditCheckVerification the creditCheckVerification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCheckVerification,
     * or with status {@code 400 (Bad Request)} if the creditCheckVerification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCheckVerification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-check-verifications/{id}")
    public ResponseEntity<CreditCheckVerification> updateCreditCheckVerification(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreditCheckVerification creditCheckVerification
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCheckVerification : {}, {}", id, creditCheckVerification);
        if (creditCheckVerification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCheckVerification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCheckVerificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCheckVerification result = creditCheckVerificationService.save(creditCheckVerification);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCheckVerification.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-check-verifications/:id} : Partial updates given fields of an existing creditCheckVerification, field will ignore if it is null
     *
     * @param id the id of the creditCheckVerification to save.
     * @param creditCheckVerification the creditCheckVerification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCheckVerification,
     * or with status {@code 400 (Bad Request)} if the creditCheckVerification is not valid,
     * or with status {@code 404 (Not Found)} if the creditCheckVerification is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCheckVerification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-check-verifications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CreditCheckVerification> partialUpdateCreditCheckVerification(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreditCheckVerification creditCheckVerification
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCheckVerification partially : {}, {}", id, creditCheckVerification);
        if (creditCheckVerification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCheckVerification.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCheckVerificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCheckVerification> result = creditCheckVerificationService.partialUpdate(creditCheckVerification);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCheckVerification.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-check-verifications} : get all the creditCheckVerifications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCheckVerifications in body.
     */
    @GetMapping("/credit-check-verifications")
    public List<CreditCheckVerification> getAllCreditCheckVerifications() {
        log.debug("REST request to get all CreditCheckVerifications");
        return creditCheckVerificationService.findAll();
    }

    /**
     * {@code GET  /credit-check-verifications/:id} : get the "id" creditCheckVerification.
     *
     * @param id the id of the creditCheckVerification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCheckVerification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-check-verifications/{id}")
    public ResponseEntity<CreditCheckVerification> getCreditCheckVerification(@PathVariable Long id) {
        log.debug("REST request to get CreditCheckVerification : {}", id);
        Optional<CreditCheckVerification> creditCheckVerification = creditCheckVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCheckVerification);
    }

    /**
     * {@code DELETE  /credit-check-verifications/:id} : delete the "id" creditCheckVerification.
     *
     * @param id the id of the creditCheckVerification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-check-verifications/{id}")
    public ResponseEntity<Void> deleteCreditCheckVerification(@PathVariable Long id) {
        log.debug("REST request to delete CreditCheckVerification : {}", id);
        creditCheckVerificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
