package com.apptium.web.rest;

import com.apptium.domain.CustISVRef;
import com.apptium.repository.CustISVRefRepository;
import com.apptium.service.CustISVRefService;
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
 * REST controller for managing {@link com.apptium.domain.CustISVRef}.
 */
@RestController
@RequestMapping("/api")
public class CustISVRefResource {

    private final Logger log = LoggerFactory.getLogger(CustISVRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustIsvRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustISVRefService custISVRefService;

    private final CustISVRefRepository custISVRefRepository;

    public CustISVRefResource(CustISVRefService custISVRefService, CustISVRefRepository custISVRefRepository) {
        this.custISVRefService = custISVRefService;
        this.custISVRefRepository = custISVRefRepository;
    }

    /**
     * {@code POST  /cust-isv-refs} : Create a new custISVRef.
     *
     * @param custISVRef the custISVRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custISVRef, or with status {@code 400 (Bad Request)} if the custISVRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-refs")
    public ResponseEntity<CustISVRef> createCustISVRef(@Valid @RequestBody CustISVRef custISVRef) throws URISyntaxException {
        log.debug("REST request to save CustISVRef : {}", custISVRef);
        if (custISVRef.getId() != null) {
            throw new BadRequestAlertException("A new custISVRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustISVRef result = custISVRefService.save(custISVRef);
        return ResponseEntity
            .created(new URI("/api/cust-isv-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-refs/:id} : Updates an existing custISVRef.
     *
     * @param id the id of the custISVRef to save.
     * @param custISVRef the custISVRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVRef,
     * or with status {@code 400 (Bad Request)} if the custISVRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custISVRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustISVRef> updateCustISVRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustISVRef custISVRef
    ) throws URISyntaxException {
        log.debug("REST request to update CustISVRef : {}, {}", id, custISVRef);
        if (custISVRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustISVRef result = custISVRefService.save(custISVRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custISVRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-refs/:id} : Partial updates given fields of an existing custISVRef, field will ignore if it is null
     *
     * @param id the id of the custISVRef to save.
     * @param custISVRef the custISVRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVRef,
     * or with status {@code 400 (Bad Request)} if the custISVRef is not valid,
     * or with status {@code 404 (Not Found)} if the custISVRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the custISVRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustISVRef> partialUpdateCustISVRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustISVRef custISVRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustISVRef partially : {}, {}", id, custISVRef);
        if (custISVRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustISVRef> result = custISVRefService.partialUpdate(custISVRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custISVRef.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-refs} : get all the custISVRefs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custISVRefs in body.
     */
    @GetMapping("/cust-isv-refs")
    public List<CustISVRef> getAllCustISVRefs() {
        log.debug("REST request to get all CustISVRefs");
        return custISVRefService.findAll();
    }

    /**
     * {@code GET  /cust-isv-refs/:id} : get the "id" custISVRef.
     *
     * @param id the id of the custISVRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custISVRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustISVRef> getCustISVRef(@PathVariable Long id) {
        log.debug("REST request to get CustISVRef : {}", id);
        Optional<CustISVRef> custISVRef = custISVRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custISVRef);
    }

    /**
     * {@code DELETE  /cust-isv-refs/:id} : delete the "id" custISVRef.
     *
     * @param id the id of the custISVRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-refs/{id}")
    public ResponseEntity<Void> deleteCustISVRef(@PathVariable Long id) {
        log.debug("REST request to delete CustISVRef : {}", id);
        custISVRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
