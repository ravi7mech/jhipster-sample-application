package com.apptium.web.rest;

import com.apptium.domain.CustBillingAcc;
import com.apptium.repository.CustBillingAccRepository;
import com.apptium.service.CustBillingAccService;
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
 * REST controller for managing {@link com.apptium.domain.CustBillingAcc}.
 */
@RestController
@RequestMapping("/api")
public class CustBillingAccResource {

    private final Logger log = LoggerFactory.getLogger(CustBillingAccResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustBillingAcc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustBillingAccService custBillingAccService;

    private final CustBillingAccRepository custBillingAccRepository;

    public CustBillingAccResource(CustBillingAccService custBillingAccService, CustBillingAccRepository custBillingAccRepository) {
        this.custBillingAccService = custBillingAccService;
        this.custBillingAccRepository = custBillingAccRepository;
    }

    /**
     * {@code POST  /cust-billing-accs} : Create a new custBillingAcc.
     *
     * @param custBillingAcc the custBillingAcc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custBillingAcc, or with status {@code 400 (Bad Request)} if the custBillingAcc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-billing-accs")
    public ResponseEntity<CustBillingAcc> createCustBillingAcc(@Valid @RequestBody CustBillingAcc custBillingAcc)
        throws URISyntaxException {
        log.debug("REST request to save CustBillingAcc : {}", custBillingAcc);
        if (custBillingAcc.getId() != null) {
            throw new BadRequestAlertException("A new custBillingAcc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustBillingAcc result = custBillingAccService.save(custBillingAcc);
        return ResponseEntity
            .created(new URI("/api/cust-billing-accs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-billing-accs/:id} : Updates an existing custBillingAcc.
     *
     * @param id the id of the custBillingAcc to save.
     * @param custBillingAcc the custBillingAcc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingAcc,
     * or with status {@code 400 (Bad Request)} if the custBillingAcc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custBillingAcc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-billing-accs/{id}")
    public ResponseEntity<CustBillingAcc> updateCustBillingAcc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustBillingAcc custBillingAcc
    ) throws URISyntaxException {
        log.debug("REST request to update CustBillingAcc : {}, {}", id, custBillingAcc);
        if (custBillingAcc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingAcc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingAccRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustBillingAcc result = custBillingAccService.save(custBillingAcc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custBillingAcc.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-billing-accs/:id} : Partial updates given fields of an existing custBillingAcc, field will ignore if it is null
     *
     * @param id the id of the custBillingAcc to save.
     * @param custBillingAcc the custBillingAcc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingAcc,
     * or with status {@code 400 (Bad Request)} if the custBillingAcc is not valid,
     * or with status {@code 404 (Not Found)} if the custBillingAcc is not found,
     * or with status {@code 500 (Internal Server Error)} if the custBillingAcc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-billing-accs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustBillingAcc> partialUpdateCustBillingAcc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustBillingAcc custBillingAcc
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustBillingAcc partially : {}, {}", id, custBillingAcc);
        if (custBillingAcc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingAcc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingAccRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustBillingAcc> result = custBillingAccService.partialUpdate(custBillingAcc);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custBillingAcc.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-billing-accs} : get all the custBillingAccs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custBillingAccs in body.
     */
    @GetMapping("/cust-billing-accs")
    public List<CustBillingAcc> getAllCustBillingAccs(@RequestParam(required = false) String filter) {
        if ("customer-is-null".equals(filter)) {
            log.debug("REST request to get all CustBillingAccs where customer is null");
            return custBillingAccService.findAllWhereCustomerIsNull();
        }
        log.debug("REST request to get all CustBillingAccs");
        return custBillingAccService.findAll();
    }

    /**
     * {@code GET  /cust-billing-accs/:id} : get the "id" custBillingAcc.
     *
     * @param id the id of the custBillingAcc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custBillingAcc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-billing-accs/{id}")
    public ResponseEntity<CustBillingAcc> getCustBillingAcc(@PathVariable Long id) {
        log.debug("REST request to get CustBillingAcc : {}", id);
        Optional<CustBillingAcc> custBillingAcc = custBillingAccService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custBillingAcc);
    }

    /**
     * {@code DELETE  /cust-billing-accs/:id} : delete the "id" custBillingAcc.
     *
     * @param id the id of the custBillingAcc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-billing-accs/{id}")
    public ResponseEntity<Void> deleteCustBillingAcc(@PathVariable Long id) {
        log.debug("REST request to delete CustBillingAcc : {}", id);
        custBillingAccService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
