package com.apptium.web.rest;

import com.apptium.domain.CustPaymentMethod;
import com.apptium.repository.CustPaymentMethodRepository;
import com.apptium.service.CustPaymentMethodService;
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
 * REST controller for managing {@link com.apptium.domain.CustPaymentMethod}.
 */
@RestController
@RequestMapping("/api")
public class CustPaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(CustPaymentMethodResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustPaymentMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustPaymentMethodService custPaymentMethodService;

    private final CustPaymentMethodRepository custPaymentMethodRepository;

    public CustPaymentMethodResource(
        CustPaymentMethodService custPaymentMethodService,
        CustPaymentMethodRepository custPaymentMethodRepository
    ) {
        this.custPaymentMethodService = custPaymentMethodService;
        this.custPaymentMethodRepository = custPaymentMethodRepository;
    }

    /**
     * {@code POST  /cust-payment-methods} : Create a new custPaymentMethod.
     *
     * @param custPaymentMethod the custPaymentMethod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custPaymentMethod, or with status {@code 400 (Bad Request)} if the custPaymentMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-payment-methods")
    public ResponseEntity<CustPaymentMethod> createCustPaymentMethod(@Valid @RequestBody CustPaymentMethod custPaymentMethod)
        throws URISyntaxException {
        log.debug("REST request to save CustPaymentMethod : {}", custPaymentMethod);
        if (custPaymentMethod.getId() != null) {
            throw new BadRequestAlertException("A new custPaymentMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustPaymentMethod result = custPaymentMethodService.save(custPaymentMethod);
        return ResponseEntity
            .created(new URI("/api/cust-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-payment-methods/:id} : Updates an existing custPaymentMethod.
     *
     * @param id the id of the custPaymentMethod to save.
     * @param custPaymentMethod the custPaymentMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPaymentMethod,
     * or with status {@code 400 (Bad Request)} if the custPaymentMethod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custPaymentMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-payment-methods/{id}")
    public ResponseEntity<CustPaymentMethod> updateCustPaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustPaymentMethod custPaymentMethod
    ) throws URISyntaxException {
        log.debug("REST request to update CustPaymentMethod : {}, {}", id, custPaymentMethod);
        if (custPaymentMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPaymentMethod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPaymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustPaymentMethod result = custPaymentMethodService.save(custPaymentMethod);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custPaymentMethod.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-payment-methods/:id} : Partial updates given fields of an existing custPaymentMethod, field will ignore if it is null
     *
     * @param id the id of the custPaymentMethod to save.
     * @param custPaymentMethod the custPaymentMethod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPaymentMethod,
     * or with status {@code 400 (Bad Request)} if the custPaymentMethod is not valid,
     * or with status {@code 404 (Not Found)} if the custPaymentMethod is not found,
     * or with status {@code 500 (Internal Server Error)} if the custPaymentMethod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-payment-methods/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustPaymentMethod> partialUpdateCustPaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustPaymentMethod custPaymentMethod
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustPaymentMethod partially : {}, {}", id, custPaymentMethod);
        if (custPaymentMethod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPaymentMethod.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPaymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustPaymentMethod> result = custPaymentMethodService.partialUpdate(custPaymentMethod);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custPaymentMethod.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-payment-methods} : get all the custPaymentMethods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custPaymentMethods in body.
     */
    @GetMapping("/cust-payment-methods")
    public List<CustPaymentMethod> getAllCustPaymentMethods() {
        log.debug("REST request to get all CustPaymentMethods");
        return custPaymentMethodService.findAll();
    }

    /**
     * {@code GET  /cust-payment-methods/:id} : get the "id" custPaymentMethod.
     *
     * @param id the id of the custPaymentMethod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custPaymentMethod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-payment-methods/{id}")
    public ResponseEntity<CustPaymentMethod> getCustPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get CustPaymentMethod : {}", id);
        Optional<CustPaymentMethod> custPaymentMethod = custPaymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custPaymentMethod);
    }

    /**
     * {@code DELETE  /cust-payment-methods/:id} : delete the "id" custPaymentMethod.
     *
     * @param id the id of the custPaymentMethod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-payment-methods/{id}")
    public ResponseEntity<Void> deleteCustPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete CustPaymentMethod : {}", id);
        custPaymentMethodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
