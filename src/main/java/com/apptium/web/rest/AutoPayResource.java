package com.apptium.web.rest;

import com.apptium.domain.AutoPay;
import com.apptium.repository.AutoPayRepository;
import com.apptium.service.AutoPayService;
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
 * REST controller for managing {@link com.apptium.domain.AutoPay}.
 */
@RestController
@RequestMapping("/api")
public class AutoPayResource {

    private final Logger log = LoggerFactory.getLogger(AutoPayResource.class);

    private static final String ENTITY_NAME = "orderMgmtAutoPay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutoPayService autoPayService;

    private final AutoPayRepository autoPayRepository;

    public AutoPayResource(AutoPayService autoPayService, AutoPayRepository autoPayRepository) {
        this.autoPayService = autoPayService;
        this.autoPayRepository = autoPayRepository;
    }

    /**
     * {@code POST  /auto-pays} : Create a new autoPay.
     *
     * @param autoPay the autoPay to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autoPay, or with status {@code 400 (Bad Request)} if the autoPay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auto-pays")
    public ResponseEntity<AutoPay> createAutoPay(@Valid @RequestBody AutoPay autoPay) throws URISyntaxException {
        log.debug("REST request to save AutoPay : {}", autoPay);
        if (autoPay.getId() != null) {
            throw new BadRequestAlertException("A new autoPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutoPay result = autoPayService.save(autoPay);
        return ResponseEntity
            .created(new URI("/api/auto-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auto-pays/:id} : Updates an existing autoPay.
     *
     * @param id the id of the autoPay to save.
     * @param autoPay the autoPay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPay,
     * or with status {@code 400 (Bad Request)} if the autoPay is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autoPay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auto-pays/{id}")
    public ResponseEntity<AutoPay> updateAutoPay(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AutoPay autoPay
    ) throws URISyntaxException {
        log.debug("REST request to update AutoPay : {}, {}", id, autoPay);
        if (autoPay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPay.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AutoPay result = autoPayService.save(autoPay);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autoPay.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auto-pays/:id} : Partial updates given fields of an existing autoPay, field will ignore if it is null
     *
     * @param id the id of the autoPay to save.
     * @param autoPay the autoPay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPay,
     * or with status {@code 400 (Bad Request)} if the autoPay is not valid,
     * or with status {@code 404 (Not Found)} if the autoPay is not found,
     * or with status {@code 500 (Internal Server Error)} if the autoPay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auto-pays/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AutoPay> partialUpdateAutoPay(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AutoPay autoPay
    ) throws URISyntaxException {
        log.debug("REST request to partial update AutoPay partially : {}, {}", id, autoPay);
        if (autoPay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPay.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AutoPay> result = autoPayService.partialUpdate(autoPay);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autoPay.getId().toString())
        );
    }

    /**
     * {@code GET  /auto-pays} : get all the autoPays.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autoPays in body.
     */
    @GetMapping("/auto-pays")
    public List<AutoPay> getAllAutoPays() {
        log.debug("REST request to get all AutoPays");
        return autoPayService.findAll();
    }

    /**
     * {@code GET  /auto-pays/:id} : get the "id" autoPay.
     *
     * @param id the id of the autoPay to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autoPay, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auto-pays/{id}")
    public ResponseEntity<AutoPay> getAutoPay(@PathVariable Long id) {
        log.debug("REST request to get AutoPay : {}", id);
        Optional<AutoPay> autoPay = autoPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autoPay);
    }

    /**
     * {@code DELETE  /auto-pays/:id} : delete the "id" autoPay.
     *
     * @param id the id of the autoPay to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auto-pays/{id}")
    public ResponseEntity<Void> deleteAutoPay(@PathVariable Long id) {
        log.debug("REST request to delete AutoPay : {}", id);
        autoPayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
