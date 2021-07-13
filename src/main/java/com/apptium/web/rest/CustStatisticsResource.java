package com.apptium.web.rest;

import com.apptium.domain.CustStatistics;
import com.apptium.repository.CustStatisticsRepository;
import com.apptium.service.CustStatisticsService;
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
 * REST controller for managing {@link com.apptium.domain.CustStatistics}.
 */
@RestController
@RequestMapping("/api")
public class CustStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(CustStatisticsResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustStatistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustStatisticsService custStatisticsService;

    private final CustStatisticsRepository custStatisticsRepository;

    public CustStatisticsResource(CustStatisticsService custStatisticsService, CustStatisticsRepository custStatisticsRepository) {
        this.custStatisticsService = custStatisticsService;
        this.custStatisticsRepository = custStatisticsRepository;
    }

    /**
     * {@code POST  /cust-statistics} : Create a new custStatistics.
     *
     * @param custStatistics the custStatistics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custStatistics, or with status {@code 400 (Bad Request)} if the custStatistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-statistics")
    public ResponseEntity<CustStatistics> createCustStatistics(@Valid @RequestBody CustStatistics custStatistics)
        throws URISyntaxException {
        log.debug("REST request to save CustStatistics : {}", custStatistics);
        if (custStatistics.getId() != null) {
            throw new BadRequestAlertException("A new custStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustStatistics result = custStatisticsService.save(custStatistics);
        return ResponseEntity
            .created(new URI("/api/cust-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-statistics/:id} : Updates an existing custStatistics.
     *
     * @param id the id of the custStatistics to save.
     * @param custStatistics the custStatistics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custStatistics,
     * or with status {@code 400 (Bad Request)} if the custStatistics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custStatistics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-statistics/{id}")
    public ResponseEntity<CustStatistics> updateCustStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustStatistics custStatistics
    ) throws URISyntaxException {
        log.debug("REST request to update CustStatistics : {}, {}", id, custStatistics);
        if (custStatistics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custStatistics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custStatisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustStatistics result = custStatisticsService.save(custStatistics);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custStatistics.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-statistics/:id} : Partial updates given fields of an existing custStatistics, field will ignore if it is null
     *
     * @param id the id of the custStatistics to save.
     * @param custStatistics the custStatistics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custStatistics,
     * or with status {@code 400 (Bad Request)} if the custStatistics is not valid,
     * or with status {@code 404 (Not Found)} if the custStatistics is not found,
     * or with status {@code 500 (Internal Server Error)} if the custStatistics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-statistics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustStatistics> partialUpdateCustStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustStatistics custStatistics
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustStatistics partially : {}, {}", id, custStatistics);
        if (custStatistics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custStatistics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custStatisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustStatistics> result = custStatisticsService.partialUpdate(custStatistics);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custStatistics.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-statistics} : get all the custStatistics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custStatistics in body.
     */
    @GetMapping("/cust-statistics")
    public List<CustStatistics> getAllCustStatistics() {
        log.debug("REST request to get all CustStatistics");
        return custStatisticsService.findAll();
    }

    /**
     * {@code GET  /cust-statistics/:id} : get the "id" custStatistics.
     *
     * @param id the id of the custStatistics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custStatistics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-statistics/{id}")
    public ResponseEntity<CustStatistics> getCustStatistics(@PathVariable Long id) {
        log.debug("REST request to get CustStatistics : {}", id);
        Optional<CustStatistics> custStatistics = custStatisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custStatistics);
    }

    /**
     * {@code DELETE  /cust-statistics/:id} : delete the "id" custStatistics.
     *
     * @param id the id of the custStatistics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-statistics/{id}")
    public ResponseEntity<Void> deleteCustStatistics(@PathVariable Long id) {
        log.debug("REST request to delete CustStatistics : {}", id);
        custStatisticsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
