package com.apptium.web.rest;

import com.apptium.domain.CustSecurityChar;
import com.apptium.repository.CustSecurityCharRepository;
import com.apptium.service.CustSecurityCharService;
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
 * REST controller for managing {@link com.apptium.domain.CustSecurityChar}.
 */
@RestController
@RequestMapping("/api")
public class CustSecurityCharResource {

    private final Logger log = LoggerFactory.getLogger(CustSecurityCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustSecurityChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustSecurityCharService custSecurityCharService;

    private final CustSecurityCharRepository custSecurityCharRepository;

    public CustSecurityCharResource(
        CustSecurityCharService custSecurityCharService,
        CustSecurityCharRepository custSecurityCharRepository
    ) {
        this.custSecurityCharService = custSecurityCharService;
        this.custSecurityCharRepository = custSecurityCharRepository;
    }

    /**
     * {@code POST  /cust-security-chars} : Create a new custSecurityChar.
     *
     * @param custSecurityChar the custSecurityChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custSecurityChar, or with status {@code 400 (Bad Request)} if the custSecurityChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-security-chars")
    public ResponseEntity<CustSecurityChar> createCustSecurityChar(@Valid @RequestBody CustSecurityChar custSecurityChar)
        throws URISyntaxException {
        log.debug("REST request to save CustSecurityChar : {}", custSecurityChar);
        if (custSecurityChar.getId() != null) {
            throw new BadRequestAlertException("A new custSecurityChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustSecurityChar result = custSecurityCharService.save(custSecurityChar);
        return ResponseEntity
            .created(new URI("/api/cust-security-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-security-chars/:id} : Updates an existing custSecurityChar.
     *
     * @param id the id of the custSecurityChar to save.
     * @param custSecurityChar the custSecurityChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custSecurityChar,
     * or with status {@code 400 (Bad Request)} if the custSecurityChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custSecurityChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-security-chars/{id}")
    public ResponseEntity<CustSecurityChar> updateCustSecurityChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustSecurityChar custSecurityChar
    ) throws URISyntaxException {
        log.debug("REST request to update CustSecurityChar : {}, {}", id, custSecurityChar);
        if (custSecurityChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custSecurityChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custSecurityCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustSecurityChar result = custSecurityCharService.save(custSecurityChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custSecurityChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-security-chars/:id} : Partial updates given fields of an existing custSecurityChar, field will ignore if it is null
     *
     * @param id the id of the custSecurityChar to save.
     * @param custSecurityChar the custSecurityChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custSecurityChar,
     * or with status {@code 400 (Bad Request)} if the custSecurityChar is not valid,
     * or with status {@code 404 (Not Found)} if the custSecurityChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the custSecurityChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-security-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustSecurityChar> partialUpdateCustSecurityChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustSecurityChar custSecurityChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustSecurityChar partially : {}, {}", id, custSecurityChar);
        if (custSecurityChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custSecurityChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custSecurityCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustSecurityChar> result = custSecurityCharService.partialUpdate(custSecurityChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custSecurityChar.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-security-chars} : get all the custSecurityChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custSecurityChars in body.
     */
    @GetMapping("/cust-security-chars")
    public List<CustSecurityChar> getAllCustSecurityChars() {
        log.debug("REST request to get all CustSecurityChars");
        return custSecurityCharService.findAll();
    }

    /**
     * {@code GET  /cust-security-chars/:id} : get the "id" custSecurityChar.
     *
     * @param id the id of the custSecurityChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custSecurityChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-security-chars/{id}")
    public ResponseEntity<CustSecurityChar> getCustSecurityChar(@PathVariable Long id) {
        log.debug("REST request to get CustSecurityChar : {}", id);
        Optional<CustSecurityChar> custSecurityChar = custSecurityCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custSecurityChar);
    }

    /**
     * {@code DELETE  /cust-security-chars/:id} : delete the "id" custSecurityChar.
     *
     * @param id the id of the custSecurityChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-security-chars/{id}")
    public ResponseEntity<Void> deleteCustSecurityChar(@PathVariable Long id) {
        log.debug("REST request to delete CustSecurityChar : {}", id);
        custSecurityCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
