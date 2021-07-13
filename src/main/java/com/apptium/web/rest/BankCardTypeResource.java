package com.apptium.web.rest;

import com.apptium.domain.BankCardType;
import com.apptium.repository.BankCardTypeRepository;
import com.apptium.service.BankCardTypeService;
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
 * REST controller for managing {@link com.apptium.domain.BankCardType}.
 */
@RestController
@RequestMapping("/api")
public class BankCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(BankCardTypeResource.class);

    private static final String ENTITY_NAME = "orderMgmtBankCardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankCardTypeService bankCardTypeService;

    private final BankCardTypeRepository bankCardTypeRepository;

    public BankCardTypeResource(BankCardTypeService bankCardTypeService, BankCardTypeRepository bankCardTypeRepository) {
        this.bankCardTypeService = bankCardTypeService;
        this.bankCardTypeRepository = bankCardTypeRepository;
    }

    /**
     * {@code POST  /bank-card-types} : Create a new bankCardType.
     *
     * @param bankCardType the bankCardType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankCardType, or with status {@code 400 (Bad Request)} if the bankCardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-card-types")
    public ResponseEntity<BankCardType> createBankCardType(@Valid @RequestBody BankCardType bankCardType) throws URISyntaxException {
        log.debug("REST request to save BankCardType : {}", bankCardType);
        if (bankCardType.getId() != null) {
            throw new BadRequestAlertException("A new bankCardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankCardType result = bankCardTypeService.save(bankCardType);
        return ResponseEntity
            .created(new URI("/api/bank-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-card-types/:id} : Updates an existing bankCardType.
     *
     * @param id the id of the bankCardType to save.
     * @param bankCardType the bankCardType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCardType,
     * or with status {@code 400 (Bad Request)} if the bankCardType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankCardType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-card-types/{id}")
    public ResponseEntity<BankCardType> updateBankCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankCardType bankCardType
    ) throws URISyntaxException {
        log.debug("REST request to update BankCardType : {}, {}", id, bankCardType);
        if (bankCardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCardType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankCardType result = bankCardTypeService.save(bankCardType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCardType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-card-types/:id} : Partial updates given fields of an existing bankCardType, field will ignore if it is null
     *
     * @param id the id of the bankCardType to save.
     * @param bankCardType the bankCardType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCardType,
     * or with status {@code 400 (Bad Request)} if the bankCardType is not valid,
     * or with status {@code 404 (Not Found)} if the bankCardType is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankCardType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-card-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BankCardType> partialUpdateBankCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankCardType bankCardType
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankCardType partially : {}, {}", id, bankCardType);
        if (bankCardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCardType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankCardType> result = bankCardTypeService.partialUpdate(bankCardType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankCardType.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-card-types} : get all the bankCardTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankCardTypes in body.
     */
    @GetMapping("/bank-card-types")
    public List<BankCardType> getAllBankCardTypes() {
        log.debug("REST request to get all BankCardTypes");
        return bankCardTypeService.findAll();
    }

    /**
     * {@code GET  /bank-card-types/:id} : get the "id" bankCardType.
     *
     * @param id the id of the bankCardType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankCardType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-card-types/{id}")
    public ResponseEntity<BankCardType> getBankCardType(@PathVariable Long id) {
        log.debug("REST request to get BankCardType : {}", id);
        Optional<BankCardType> bankCardType = bankCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankCardType);
    }

    /**
     * {@code DELETE  /bank-card-types/:id} : delete the "id" bankCardType.
     *
     * @param id the id of the bankCardType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-card-types/{id}")
    public ResponseEntity<Void> deleteBankCardType(@PathVariable Long id) {
        log.debug("REST request to delete BankCardType : {}", id);
        bankCardTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
