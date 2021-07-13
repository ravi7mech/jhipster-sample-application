package com.apptium.web.rest;

import com.apptium.domain.CustChar;
import com.apptium.repository.CustCharRepository;
import com.apptium.service.CustCharService;
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
 * REST controller for managing {@link com.apptium.domain.CustChar}.
 */
@RestController
@RequestMapping("/api")
public class CustCharResource {

    private final Logger log = LoggerFactory.getLogger(CustCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCharService custCharService;

    private final CustCharRepository custCharRepository;

    public CustCharResource(CustCharService custCharService, CustCharRepository custCharRepository) {
        this.custCharService = custCharService;
        this.custCharRepository = custCharRepository;
    }

    /**
     * {@code POST  /cust-chars} : Create a new custChar.
     *
     * @param custChar the custChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custChar, or with status {@code 400 (Bad Request)} if the custChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-chars")
    public ResponseEntity<CustChar> createCustChar(@Valid @RequestBody CustChar custChar) throws URISyntaxException {
        log.debug("REST request to save CustChar : {}", custChar);
        if (custChar.getId() != null) {
            throw new BadRequestAlertException("A new custChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustChar result = custCharService.save(custChar);
        return ResponseEntity
            .created(new URI("/api/cust-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-chars/:id} : Updates an existing custChar.
     *
     * @param id the id of the custChar to save.
     * @param custChar the custChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custChar,
     * or with status {@code 400 (Bad Request)} if the custChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-chars/{id}")
    public ResponseEntity<CustChar> updateCustChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustChar custChar
    ) throws URISyntaxException {
        log.debug("REST request to update CustChar : {}, {}", id, custChar);
        if (custChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustChar result = custCharService.save(custChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-chars/:id} : Partial updates given fields of an existing custChar, field will ignore if it is null
     *
     * @param id the id of the custChar to save.
     * @param custChar the custChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custChar,
     * or with status {@code 400 (Bad Request)} if the custChar is not valid,
     * or with status {@code 404 (Not Found)} if the custChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the custChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustChar> partialUpdateCustChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustChar custChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustChar partially : {}, {}", id, custChar);
        if (custChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustChar> result = custCharService.partialUpdate(custChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custChar.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-chars} : get all the custChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custChars in body.
     */
    @GetMapping("/cust-chars")
    public List<CustChar> getAllCustChars() {
        log.debug("REST request to get all CustChars");
        return custCharService.findAll();
    }

    /**
     * {@code GET  /cust-chars/:id} : get the "id" custChar.
     *
     * @param id the id of the custChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-chars/{id}")
    public ResponseEntity<CustChar> getCustChar(@PathVariable Long id) {
        log.debug("REST request to get CustChar : {}", id);
        Optional<CustChar> custChar = custCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custChar);
    }

    /**
     * {@code DELETE  /cust-chars/:id} : delete the "id" custChar.
     *
     * @param id the id of the custChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-chars/{id}")
    public ResponseEntity<Void> deleteCustChar(@PathVariable Long id) {
        log.debug("REST request to delete CustChar : {}", id);
        custCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
