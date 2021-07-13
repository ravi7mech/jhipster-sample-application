package com.apptium.web.rest;

import com.apptium.domain.CustContactChar;
import com.apptium.repository.CustContactCharRepository;
import com.apptium.service.CustContactCharService;
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
 * REST controller for managing {@link com.apptium.domain.CustContactChar}.
 */
@RestController
@RequestMapping("/api")
public class CustContactCharResource {

    private final Logger log = LoggerFactory.getLogger(CustContactCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustContactChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustContactCharService custContactCharService;

    private final CustContactCharRepository custContactCharRepository;

    public CustContactCharResource(CustContactCharService custContactCharService, CustContactCharRepository custContactCharRepository) {
        this.custContactCharService = custContactCharService;
        this.custContactCharRepository = custContactCharRepository;
    }

    /**
     * {@code POST  /cust-contact-chars} : Create a new custContactChar.
     *
     * @param custContactChar the custContactChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custContactChar, or with status {@code 400 (Bad Request)} if the custContactChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-contact-chars")
    public ResponseEntity<CustContactChar> createCustContactChar(@Valid @RequestBody CustContactChar custContactChar)
        throws URISyntaxException {
        log.debug("REST request to save CustContactChar : {}", custContactChar);
        if (custContactChar.getId() != null) {
            throw new BadRequestAlertException("A new custContactChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustContactChar result = custContactCharService.save(custContactChar);
        return ResponseEntity
            .created(new URI("/api/cust-contact-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-contact-chars/:id} : Updates an existing custContactChar.
     *
     * @param id the id of the custContactChar to save.
     * @param custContactChar the custContactChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactChar,
     * or with status {@code 400 (Bad Request)} if the custContactChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custContactChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-contact-chars/{id}")
    public ResponseEntity<CustContactChar> updateCustContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustContactChar custContactChar
    ) throws URISyntaxException {
        log.debug("REST request to update CustContactChar : {}, {}", id, custContactChar);
        if (custContactChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustContactChar result = custContactCharService.save(custContactChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custContactChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-contact-chars/:id} : Partial updates given fields of an existing custContactChar, field will ignore if it is null
     *
     * @param id the id of the custContactChar to save.
     * @param custContactChar the custContactChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactChar,
     * or with status {@code 400 (Bad Request)} if the custContactChar is not valid,
     * or with status {@code 404 (Not Found)} if the custContactChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the custContactChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-contact-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustContactChar> partialUpdateCustContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustContactChar custContactChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustContactChar partially : {}, {}", id, custContactChar);
        if (custContactChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustContactChar> result = custContactCharService.partialUpdate(custContactChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custContactChar.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-contact-chars} : get all the custContactChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custContactChars in body.
     */
    @GetMapping("/cust-contact-chars")
    public List<CustContactChar> getAllCustContactChars() {
        log.debug("REST request to get all CustContactChars");
        return custContactCharService.findAll();
    }

    /**
     * {@code GET  /cust-contact-chars/:id} : get the "id" custContactChar.
     *
     * @param id the id of the custContactChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custContactChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-contact-chars/{id}")
    public ResponseEntity<CustContactChar> getCustContactChar(@PathVariable Long id) {
        log.debug("REST request to get CustContactChar : {}", id);
        Optional<CustContactChar> custContactChar = custContactCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custContactChar);
    }

    /**
     * {@code DELETE  /cust-contact-chars/:id} : delete the "id" custContactChar.
     *
     * @param id the id of the custContactChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-contact-chars/{id}")
    public ResponseEntity<Void> deleteCustContactChar(@PathVariable Long id) {
        log.debug("REST request to delete CustContactChar : {}", id);
        custContactCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
