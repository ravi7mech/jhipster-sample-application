package com.apptium.web.rest;

import com.apptium.domain.CustCommunicationRef;
import com.apptium.repository.CustCommunicationRefRepository;
import com.apptium.service.CustCommunicationRefService;
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
 * REST controller for managing {@link com.apptium.domain.CustCommunicationRef}.
 */
@RestController
@RequestMapping("/api")
public class CustCommunicationRefResource {

    private final Logger log = LoggerFactory.getLogger(CustCommunicationRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustCommunicationRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCommunicationRefService custCommunicationRefService;

    private final CustCommunicationRefRepository custCommunicationRefRepository;

    public CustCommunicationRefResource(
        CustCommunicationRefService custCommunicationRefService,
        CustCommunicationRefRepository custCommunicationRefRepository
    ) {
        this.custCommunicationRefService = custCommunicationRefService;
        this.custCommunicationRefRepository = custCommunicationRefRepository;
    }

    /**
     * {@code POST  /cust-communication-refs} : Create a new custCommunicationRef.
     *
     * @param custCommunicationRef the custCommunicationRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custCommunicationRef, or with status {@code 400 (Bad Request)} if the custCommunicationRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-communication-refs")
    public ResponseEntity<CustCommunicationRef> createCustCommunicationRef(@Valid @RequestBody CustCommunicationRef custCommunicationRef)
        throws URISyntaxException {
        log.debug("REST request to save CustCommunicationRef : {}", custCommunicationRef);
        if (custCommunicationRef.getId() != null) {
            throw new BadRequestAlertException("A new custCommunicationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustCommunicationRef result = custCommunicationRefService.save(custCommunicationRef);
        return ResponseEntity
            .created(new URI("/api/cust-communication-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-communication-refs/:id} : Updates an existing custCommunicationRef.
     *
     * @param id the id of the custCommunicationRef to save.
     * @param custCommunicationRef the custCommunicationRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCommunicationRef,
     * or with status {@code 400 (Bad Request)} if the custCommunicationRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custCommunicationRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-communication-refs/{id}")
    public ResponseEntity<CustCommunicationRef> updateCustCommunicationRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustCommunicationRef custCommunicationRef
    ) throws URISyntaxException {
        log.debug("REST request to update CustCommunicationRef : {}, {}", id, custCommunicationRef);
        if (custCommunicationRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCommunicationRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCommunicationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustCommunicationRef result = custCommunicationRefService.save(custCommunicationRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custCommunicationRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-communication-refs/:id} : Partial updates given fields of an existing custCommunicationRef, field will ignore if it is null
     *
     * @param id the id of the custCommunicationRef to save.
     * @param custCommunicationRef the custCommunicationRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCommunicationRef,
     * or with status {@code 400 (Bad Request)} if the custCommunicationRef is not valid,
     * or with status {@code 404 (Not Found)} if the custCommunicationRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the custCommunicationRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-communication-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustCommunicationRef> partialUpdateCustCommunicationRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustCommunicationRef custCommunicationRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustCommunicationRef partially : {}, {}", id, custCommunicationRef);
        if (custCommunicationRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCommunicationRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCommunicationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustCommunicationRef> result = custCommunicationRefService.partialUpdate(custCommunicationRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custCommunicationRef.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-communication-refs} : get all the custCommunicationRefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custCommunicationRefs in body.
     */
    @GetMapping("/cust-communication-refs")
    public List<CustCommunicationRef> getAllCustCommunicationRefs() {
        log.debug("REST request to get all CustCommunicationRefs");
        return custCommunicationRefService.findAll();
    }

    /**
     * {@code GET  /cust-communication-refs/:id} : get the "id" custCommunicationRef.
     *
     * @param id the id of the custCommunicationRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custCommunicationRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-communication-refs/{id}")
    public ResponseEntity<CustCommunicationRef> getCustCommunicationRef(@PathVariable Long id) {
        log.debug("REST request to get CustCommunicationRef : {}", id);
        Optional<CustCommunicationRef> custCommunicationRef = custCommunicationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custCommunicationRef);
    }

    /**
     * {@code DELETE  /cust-communication-refs/:id} : delete the "id" custCommunicationRef.
     *
     * @param id the id of the custCommunicationRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-communication-refs/{id}")
    public ResponseEntity<Void> deleteCustCommunicationRef(@PathVariable Long id) {
        log.debug("REST request to delete CustCommunicationRef : {}", id);
        custCommunicationRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
