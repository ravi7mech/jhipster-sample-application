package com.apptium.web.rest;

import com.apptium.domain.CustCreditProfile;
import com.apptium.repository.CustCreditProfileRepository;
import com.apptium.service.CustCreditProfileService;
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
 * REST controller for managing {@link com.apptium.domain.CustCreditProfile}.
 */
@RestController
@RequestMapping("/api")
public class CustCreditProfileResource {

    private final Logger log = LoggerFactory.getLogger(CustCreditProfileResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustCreditProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCreditProfileService custCreditProfileService;

    private final CustCreditProfileRepository custCreditProfileRepository;

    public CustCreditProfileResource(
        CustCreditProfileService custCreditProfileService,
        CustCreditProfileRepository custCreditProfileRepository
    ) {
        this.custCreditProfileService = custCreditProfileService;
        this.custCreditProfileRepository = custCreditProfileRepository;
    }

    /**
     * {@code POST  /cust-credit-profiles} : Create a new custCreditProfile.
     *
     * @param custCreditProfile the custCreditProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custCreditProfile, or with status {@code 400 (Bad Request)} if the custCreditProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-credit-profiles")
    public ResponseEntity<CustCreditProfile> createCustCreditProfile(@Valid @RequestBody CustCreditProfile custCreditProfile)
        throws URISyntaxException {
        log.debug("REST request to save CustCreditProfile : {}", custCreditProfile);
        if (custCreditProfile.getId() != null) {
            throw new BadRequestAlertException("A new custCreditProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustCreditProfile result = custCreditProfileService.save(custCreditProfile);
        return ResponseEntity
            .created(new URI("/api/cust-credit-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-credit-profiles/:id} : Updates an existing custCreditProfile.
     *
     * @param id the id of the custCreditProfile to save.
     * @param custCreditProfile the custCreditProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCreditProfile,
     * or with status {@code 400 (Bad Request)} if the custCreditProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custCreditProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<CustCreditProfile> updateCustCreditProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustCreditProfile custCreditProfile
    ) throws URISyntaxException {
        log.debug("REST request to update CustCreditProfile : {}, {}", id, custCreditProfile);
        if (custCreditProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCreditProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCreditProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustCreditProfile result = custCreditProfileService.save(custCreditProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custCreditProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-credit-profiles/:id} : Partial updates given fields of an existing custCreditProfile, field will ignore if it is null
     *
     * @param id the id of the custCreditProfile to save.
     * @param custCreditProfile the custCreditProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCreditProfile,
     * or with status {@code 400 (Bad Request)} if the custCreditProfile is not valid,
     * or with status {@code 404 (Not Found)} if the custCreditProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the custCreditProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-credit-profiles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustCreditProfile> partialUpdateCustCreditProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustCreditProfile custCreditProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustCreditProfile partially : {}, {}", id, custCreditProfile);
        if (custCreditProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCreditProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCreditProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustCreditProfile> result = custCreditProfileService.partialUpdate(custCreditProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custCreditProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-credit-profiles} : get all the custCreditProfiles.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custCreditProfiles in body.
     */
    @GetMapping("/cust-credit-profiles")
    public List<CustCreditProfile> getAllCustCreditProfiles(@RequestParam(required = false) String filter) {
        if ("customer-is-null".equals(filter)) {
            log.debug("REST request to get all CustCreditProfiles where customer is null");
            return custCreditProfileService.findAllWhereCustomerIsNull();
        }
        log.debug("REST request to get all CustCreditProfiles");
        return custCreditProfileService.findAll();
    }

    /**
     * {@code GET  /cust-credit-profiles/:id} : get the "id" custCreditProfile.
     *
     * @param id the id of the custCreditProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custCreditProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<CustCreditProfile> getCustCreditProfile(@PathVariable Long id) {
        log.debug("REST request to get CustCreditProfile : {}", id);
        Optional<CustCreditProfile> custCreditProfile = custCreditProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custCreditProfile);
    }

    /**
     * {@code DELETE  /cust-credit-profiles/:id} : delete the "id" custCreditProfile.
     *
     * @param id the id of the custCreditProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<Void> deleteCustCreditProfile(@PathVariable Long id) {
        log.debug("REST request to delete CustCreditProfile : {}", id);
        custCreditProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
