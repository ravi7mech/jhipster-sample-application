package com.apptium.web.rest;

import com.apptium.domain.CustPasswordChar;
import com.apptium.repository.CustPasswordCharRepository;
import com.apptium.service.CustPasswordCharService;
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
 * REST controller for managing {@link com.apptium.domain.CustPasswordChar}.
 */
@RestController
@RequestMapping("/api")
public class CustPasswordCharResource {

    private final Logger log = LoggerFactory.getLogger(CustPasswordCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustPasswordChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustPasswordCharService custPasswordCharService;

    private final CustPasswordCharRepository custPasswordCharRepository;

    public CustPasswordCharResource(
        CustPasswordCharService custPasswordCharService,
        CustPasswordCharRepository custPasswordCharRepository
    ) {
        this.custPasswordCharService = custPasswordCharService;
        this.custPasswordCharRepository = custPasswordCharRepository;
    }

    /**
     * {@code POST  /cust-password-chars} : Create a new custPasswordChar.
     *
     * @param custPasswordChar the custPasswordChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custPasswordChar, or with status {@code 400 (Bad Request)} if the custPasswordChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-password-chars")
    public ResponseEntity<CustPasswordChar> createCustPasswordChar(@Valid @RequestBody CustPasswordChar custPasswordChar)
        throws URISyntaxException {
        log.debug("REST request to save CustPasswordChar : {}", custPasswordChar);
        if (custPasswordChar.getId() != null) {
            throw new BadRequestAlertException("A new custPasswordChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustPasswordChar result = custPasswordCharService.save(custPasswordChar);
        return ResponseEntity
            .created(new URI("/api/cust-password-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-password-chars/:id} : Updates an existing custPasswordChar.
     *
     * @param id the id of the custPasswordChar to save.
     * @param custPasswordChar the custPasswordChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPasswordChar,
     * or with status {@code 400 (Bad Request)} if the custPasswordChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custPasswordChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-password-chars/{id}")
    public ResponseEntity<CustPasswordChar> updateCustPasswordChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustPasswordChar custPasswordChar
    ) throws URISyntaxException {
        log.debug("REST request to update CustPasswordChar : {}, {}", id, custPasswordChar);
        if (custPasswordChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPasswordChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPasswordCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustPasswordChar result = custPasswordCharService.save(custPasswordChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custPasswordChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-password-chars/:id} : Partial updates given fields of an existing custPasswordChar, field will ignore if it is null
     *
     * @param id the id of the custPasswordChar to save.
     * @param custPasswordChar the custPasswordChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPasswordChar,
     * or with status {@code 400 (Bad Request)} if the custPasswordChar is not valid,
     * or with status {@code 404 (Not Found)} if the custPasswordChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the custPasswordChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-password-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustPasswordChar> partialUpdateCustPasswordChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustPasswordChar custPasswordChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustPasswordChar partially : {}, {}", id, custPasswordChar);
        if (custPasswordChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPasswordChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPasswordCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustPasswordChar> result = custPasswordCharService.partialUpdate(custPasswordChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custPasswordChar.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-password-chars} : get all the custPasswordChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custPasswordChars in body.
     */
    @GetMapping("/cust-password-chars")
    public List<CustPasswordChar> getAllCustPasswordChars() {
        log.debug("REST request to get all CustPasswordChars");
        return custPasswordCharService.findAll();
    }

    /**
     * {@code GET  /cust-password-chars/:id} : get the "id" custPasswordChar.
     *
     * @param id the id of the custPasswordChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custPasswordChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-password-chars/{id}")
    public ResponseEntity<CustPasswordChar> getCustPasswordChar(@PathVariable Long id) {
        log.debug("REST request to get CustPasswordChar : {}", id);
        Optional<CustPasswordChar> custPasswordChar = custPasswordCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custPasswordChar);
    }

    /**
     * {@code DELETE  /cust-password-chars/:id} : delete the "id" custPasswordChar.
     *
     * @param id the id of the custPasswordChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-password-chars/{id}")
    public ResponseEntity<Void> deleteCustPasswordChar(@PathVariable Long id) {
        log.debug("REST request to delete CustPasswordChar : {}", id);
        custPasswordCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
