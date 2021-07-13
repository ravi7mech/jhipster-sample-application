package com.apptium.web.rest;

import com.apptium.domain.CustNewsLetterConfig;
import com.apptium.repository.CustNewsLetterConfigRepository;
import com.apptium.service.CustNewsLetterConfigService;
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
 * REST controller for managing {@link com.apptium.domain.CustNewsLetterConfig}.
 */
@RestController
@RequestMapping("/api")
public class CustNewsLetterConfigResource {

    private final Logger log = LoggerFactory.getLogger(CustNewsLetterConfigResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustNewsLetterConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustNewsLetterConfigService custNewsLetterConfigService;

    private final CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    public CustNewsLetterConfigResource(
        CustNewsLetterConfigService custNewsLetterConfigService,
        CustNewsLetterConfigRepository custNewsLetterConfigRepository
    ) {
        this.custNewsLetterConfigService = custNewsLetterConfigService;
        this.custNewsLetterConfigRepository = custNewsLetterConfigRepository;
    }

    /**
     * {@code POST  /cust-news-letter-configs} : Create a new custNewsLetterConfig.
     *
     * @param custNewsLetterConfig the custNewsLetterConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custNewsLetterConfig, or with status {@code 400 (Bad Request)} if the custNewsLetterConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-news-letter-configs")
    public ResponseEntity<CustNewsLetterConfig> createCustNewsLetterConfig(@Valid @RequestBody CustNewsLetterConfig custNewsLetterConfig)
        throws URISyntaxException {
        log.debug("REST request to save CustNewsLetterConfig : {}", custNewsLetterConfig);
        if (custNewsLetterConfig.getId() != null) {
            throw new BadRequestAlertException("A new custNewsLetterConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustNewsLetterConfig result = custNewsLetterConfigService.save(custNewsLetterConfig);
        return ResponseEntity
            .created(new URI("/api/cust-news-letter-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-news-letter-configs/:id} : Updates an existing custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfig to save.
     * @param custNewsLetterConfig the custNewsLetterConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custNewsLetterConfig,
     * or with status {@code 400 (Bad Request)} if the custNewsLetterConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custNewsLetterConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<CustNewsLetterConfig> updateCustNewsLetterConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustNewsLetterConfig custNewsLetterConfig
    ) throws URISyntaxException {
        log.debug("REST request to update CustNewsLetterConfig : {}, {}", id, custNewsLetterConfig);
        if (custNewsLetterConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custNewsLetterConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custNewsLetterConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustNewsLetterConfig result = custNewsLetterConfigService.save(custNewsLetterConfig);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custNewsLetterConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-news-letter-configs/:id} : Partial updates given fields of an existing custNewsLetterConfig, field will ignore if it is null
     *
     * @param id the id of the custNewsLetterConfig to save.
     * @param custNewsLetterConfig the custNewsLetterConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custNewsLetterConfig,
     * or with status {@code 400 (Bad Request)} if the custNewsLetterConfig is not valid,
     * or with status {@code 404 (Not Found)} if the custNewsLetterConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the custNewsLetterConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-news-letter-configs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustNewsLetterConfig> partialUpdateCustNewsLetterConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustNewsLetterConfig custNewsLetterConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustNewsLetterConfig partially : {}, {}", id, custNewsLetterConfig);
        if (custNewsLetterConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custNewsLetterConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custNewsLetterConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustNewsLetterConfig> result = custNewsLetterConfigService.partialUpdate(custNewsLetterConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custNewsLetterConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-news-letter-configs} : get all the custNewsLetterConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custNewsLetterConfigs in body.
     */
    @GetMapping("/cust-news-letter-configs")
    public List<CustNewsLetterConfig> getAllCustNewsLetterConfigs() {
        log.debug("REST request to get all CustNewsLetterConfigs");
        return custNewsLetterConfigService.findAll();
    }

    /**
     * {@code GET  /cust-news-letter-configs/:id} : get the "id" custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custNewsLetterConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<CustNewsLetterConfig> getCustNewsLetterConfig(@PathVariable Long id) {
        log.debug("REST request to get CustNewsLetterConfig : {}", id);
        Optional<CustNewsLetterConfig> custNewsLetterConfig = custNewsLetterConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custNewsLetterConfig);
    }

    /**
     * {@code DELETE  /cust-news-letter-configs/:id} : delete the "id" custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<Void> deleteCustNewsLetterConfig(@PathVariable Long id) {
        log.debug("REST request to delete CustNewsLetterConfig : {}", id);
        custNewsLetterConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
