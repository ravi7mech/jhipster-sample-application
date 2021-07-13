package com.apptium.web.rest;

import com.apptium.domain.CustRelParty;
import com.apptium.repository.CustRelPartyRepository;
import com.apptium.service.CustRelPartyService;
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
 * REST controller for managing {@link com.apptium.domain.CustRelParty}.
 */
@RestController
@RequestMapping("/api")
public class CustRelPartyResource {

    private final Logger log = LoggerFactory.getLogger(CustRelPartyResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustRelParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustRelPartyService custRelPartyService;

    private final CustRelPartyRepository custRelPartyRepository;

    public CustRelPartyResource(CustRelPartyService custRelPartyService, CustRelPartyRepository custRelPartyRepository) {
        this.custRelPartyService = custRelPartyService;
        this.custRelPartyRepository = custRelPartyRepository;
    }

    /**
     * {@code POST  /cust-rel-parties} : Create a new custRelParty.
     *
     * @param custRelParty the custRelParty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custRelParty, or with status {@code 400 (Bad Request)} if the custRelParty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-rel-parties")
    public ResponseEntity<CustRelParty> createCustRelParty(@Valid @RequestBody CustRelParty custRelParty) throws URISyntaxException {
        log.debug("REST request to save CustRelParty : {}", custRelParty);
        if (custRelParty.getId() != null) {
            throw new BadRequestAlertException("A new custRelParty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustRelParty result = custRelPartyService.save(custRelParty);
        return ResponseEntity
            .created(new URI("/api/cust-rel-parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-rel-parties/:id} : Updates an existing custRelParty.
     *
     * @param id the id of the custRelParty to save.
     * @param custRelParty the custRelParty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custRelParty,
     * or with status {@code 400 (Bad Request)} if the custRelParty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custRelParty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-rel-parties/{id}")
    public ResponseEntity<CustRelParty> updateCustRelParty(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustRelParty custRelParty
    ) throws URISyntaxException {
        log.debug("REST request to update CustRelParty : {}, {}", id, custRelParty);
        if (custRelParty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custRelParty.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custRelPartyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustRelParty result = custRelPartyService.save(custRelParty);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custRelParty.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-rel-parties/:id} : Partial updates given fields of an existing custRelParty, field will ignore if it is null
     *
     * @param id the id of the custRelParty to save.
     * @param custRelParty the custRelParty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custRelParty,
     * or with status {@code 400 (Bad Request)} if the custRelParty is not valid,
     * or with status {@code 404 (Not Found)} if the custRelParty is not found,
     * or with status {@code 500 (Internal Server Error)} if the custRelParty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-rel-parties/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustRelParty> partialUpdateCustRelParty(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustRelParty custRelParty
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustRelParty partially : {}, {}", id, custRelParty);
        if (custRelParty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custRelParty.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custRelPartyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustRelParty> result = custRelPartyService.partialUpdate(custRelParty);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custRelParty.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-rel-parties} : get all the custRelParties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custRelParties in body.
     */
    @GetMapping("/cust-rel-parties")
    public List<CustRelParty> getAllCustRelParties() {
        log.debug("REST request to get all CustRelParties");
        return custRelPartyService.findAll();
    }

    /**
     * {@code GET  /cust-rel-parties/:id} : get the "id" custRelParty.
     *
     * @param id the id of the custRelParty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custRelParty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-rel-parties/{id}")
    public ResponseEntity<CustRelParty> getCustRelParty(@PathVariable Long id) {
        log.debug("REST request to get CustRelParty : {}", id);
        Optional<CustRelParty> custRelParty = custRelPartyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custRelParty);
    }

    /**
     * {@code DELETE  /cust-rel-parties/:id} : delete the "id" custRelParty.
     *
     * @param id the id of the custRelParty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-rel-parties/{id}")
    public ResponseEntity<Void> deleteCustRelParty(@PathVariable Long id) {
        log.debug("REST request to delete CustRelParty : {}", id);
        custRelPartyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
