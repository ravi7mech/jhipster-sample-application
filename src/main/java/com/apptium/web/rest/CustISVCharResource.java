package com.apptium.web.rest;

import com.apptium.domain.CustISVChar;
import com.apptium.repository.CustISVCharRepository;
import com.apptium.service.CustISVCharService;
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
 * REST controller for managing {@link com.apptium.domain.CustISVChar}.
 */
@RestController
@RequestMapping("/api")
public class CustISVCharResource {

    private final Logger log = LoggerFactory.getLogger(CustISVCharResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustIsvChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustISVCharService custISVCharService;

    private final CustISVCharRepository custISVCharRepository;

    public CustISVCharResource(CustISVCharService custISVCharService, CustISVCharRepository custISVCharRepository) {
        this.custISVCharService = custISVCharService;
        this.custISVCharRepository = custISVCharRepository;
    }

    /**
     * {@code POST  /cust-isv-chars} : Create a new custISVChar.
     *
     * @param custISVChar the custISVChar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custISVChar, or with status {@code 400 (Bad Request)} if the custISVChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-chars")
    public ResponseEntity<CustISVChar> createCustISVChar(@Valid @RequestBody CustISVChar custISVChar) throws URISyntaxException {
        log.debug("REST request to save CustISVChar : {}", custISVChar);
        if (custISVChar.getId() != null) {
            throw new BadRequestAlertException("A new custISVChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustISVChar result = custISVCharService.save(custISVChar);
        return ResponseEntity
            .created(new URI("/api/cust-isv-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-chars/:id} : Updates an existing custISVChar.
     *
     * @param id the id of the custISVChar to save.
     * @param custISVChar the custISVChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVChar,
     * or with status {@code 400 (Bad Request)} if the custISVChar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custISVChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustISVChar> updateCustISVChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustISVChar custISVChar
    ) throws URISyntaxException {
        log.debug("REST request to update CustISVChar : {}, {}", id, custISVChar);
        if (custISVChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustISVChar result = custISVCharService.save(custISVChar);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custISVChar.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-chars/:id} : Partial updates given fields of an existing custISVChar, field will ignore if it is null
     *
     * @param id the id of the custISVChar to save.
     * @param custISVChar the custISVChar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVChar,
     * or with status {@code 400 (Bad Request)} if the custISVChar is not valid,
     * or with status {@code 404 (Not Found)} if the custISVChar is not found,
     * or with status {@code 500 (Internal Server Error)} if the custISVChar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustISVChar> partialUpdateCustISVChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustISVChar custISVChar
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustISVChar partially : {}, {}", id, custISVChar);
        if (custISVChar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVChar.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustISVChar> result = custISVCharService.partialUpdate(custISVChar);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custISVChar.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-chars} : get all the custISVChars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custISVChars in body.
     */
    @GetMapping("/cust-isv-chars")
    public List<CustISVChar> getAllCustISVChars() {
        log.debug("REST request to get all CustISVChars");
        return custISVCharService.findAll();
    }

    /**
     * {@code GET  /cust-isv-chars/:id} : get the "id" custISVChar.
     *
     * @param id the id of the custISVChar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custISVChar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustISVChar> getCustISVChar(@PathVariable Long id) {
        log.debug("REST request to get CustISVChar : {}", id);
        Optional<CustISVChar> custISVChar = custISVCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custISVChar);
    }

    /**
     * {@code DELETE  /cust-isv-chars/:id} : delete the "id" custISVChar.
     *
     * @param id the id of the custISVChar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-chars/{id}")
    public ResponseEntity<Void> deleteCustISVChar(@PathVariable Long id) {
        log.debug("REST request to delete CustISVChar : {}", id);
        custISVCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
