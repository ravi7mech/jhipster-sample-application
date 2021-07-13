package com.apptium.web.rest;

import com.apptium.domain.CustBillingRef;
import com.apptium.repository.CustBillingRefRepository;
import com.apptium.service.CustBillingRefService;
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
 * REST controller for managing {@link com.apptium.domain.CustBillingRef}.
 */
@RestController
@RequestMapping("/api")
public class CustBillingRefResource {

    private final Logger log = LoggerFactory.getLogger(CustBillingRefResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustBillingRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustBillingRefService custBillingRefService;

    private final CustBillingRefRepository custBillingRefRepository;

    public CustBillingRefResource(CustBillingRefService custBillingRefService, CustBillingRefRepository custBillingRefRepository) {
        this.custBillingRefService = custBillingRefService;
        this.custBillingRefRepository = custBillingRefRepository;
    }

    /**
     * {@code POST  /cust-billing-refs} : Create a new custBillingRef.
     *
     * @param custBillingRef the custBillingRef to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custBillingRef, or with status {@code 400 (Bad Request)} if the custBillingRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-billing-refs")
    public ResponseEntity<CustBillingRef> createCustBillingRef(@Valid @RequestBody CustBillingRef custBillingRef)
        throws URISyntaxException {
        log.debug("REST request to save CustBillingRef : {}", custBillingRef);
        if (custBillingRef.getId() != null) {
            throw new BadRequestAlertException("A new custBillingRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustBillingRef result = custBillingRefService.save(custBillingRef);
        return ResponseEntity
            .created(new URI("/api/cust-billing-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-billing-refs/:id} : Updates an existing custBillingRef.
     *
     * @param id the id of the custBillingRef to save.
     * @param custBillingRef the custBillingRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingRef,
     * or with status {@code 400 (Bad Request)} if the custBillingRef is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custBillingRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-billing-refs/{id}")
    public ResponseEntity<CustBillingRef> updateCustBillingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustBillingRef custBillingRef
    ) throws URISyntaxException {
        log.debug("REST request to update CustBillingRef : {}, {}", id, custBillingRef);
        if (custBillingRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustBillingRef result = custBillingRefService.save(custBillingRef);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custBillingRef.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-billing-refs/:id} : Partial updates given fields of an existing custBillingRef, field will ignore if it is null
     *
     * @param id the id of the custBillingRef to save.
     * @param custBillingRef the custBillingRef to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingRef,
     * or with status {@code 400 (Bad Request)} if the custBillingRef is not valid,
     * or with status {@code 404 (Not Found)} if the custBillingRef is not found,
     * or with status {@code 500 (Internal Server Error)} if the custBillingRef couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-billing-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustBillingRef> partialUpdateCustBillingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustBillingRef custBillingRef
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustBillingRef partially : {}, {}", id, custBillingRef);
        if (custBillingRef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingRef.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustBillingRef> result = custBillingRefService.partialUpdate(custBillingRef);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custBillingRef.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-billing-refs} : get all the custBillingRefs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custBillingRefs in body.
     */
    @GetMapping("/cust-billing-refs")
    public List<CustBillingRef> getAllCustBillingRefs(@RequestParam(required = false) String filter) {
        if ("customer-is-null".equals(filter)) {
            log.debug("REST request to get all CustBillingRefs where customer is null");
            return custBillingRefService.findAllWhereCustomerIsNull();
        }
        log.debug("REST request to get all CustBillingRefs");
        return custBillingRefService.findAll();
    }

    /**
     * {@code GET  /cust-billing-refs/:id} : get the "id" custBillingRef.
     *
     * @param id the id of the custBillingRef to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custBillingRef, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-billing-refs/{id}")
    public ResponseEntity<CustBillingRef> getCustBillingRef(@PathVariable Long id) {
        log.debug("REST request to get CustBillingRef : {}", id);
        Optional<CustBillingRef> custBillingRef = custBillingRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custBillingRef);
    }

    /**
     * {@code DELETE  /cust-billing-refs/:id} : delete the "id" custBillingRef.
     *
     * @param id the id of the custBillingRef to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-billing-refs/{id}")
    public ResponseEntity<Void> deleteCustBillingRef(@PathVariable Long id) {
        log.debug("REST request to delete CustBillingRef : {}", id);
        custBillingRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
