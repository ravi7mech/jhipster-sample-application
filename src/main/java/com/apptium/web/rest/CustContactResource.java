package com.apptium.web.rest;

import com.apptium.domain.CustContact;
import com.apptium.repository.CustContactRepository;
import com.apptium.service.CustContactService;
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
 * REST controller for managing {@link com.apptium.domain.CustContact}.
 */
@RestController
@RequestMapping("/api")
public class CustContactResource {

    private final Logger log = LoggerFactory.getLogger(CustContactResource.class);

    private static final String ENTITY_NAME = "orderMgmtCustContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustContactService custContactService;

    private final CustContactRepository custContactRepository;

    public CustContactResource(CustContactService custContactService, CustContactRepository custContactRepository) {
        this.custContactService = custContactService;
        this.custContactRepository = custContactRepository;
    }

    /**
     * {@code POST  /cust-contacts} : Create a new custContact.
     *
     * @param custContact the custContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custContact, or with status {@code 400 (Bad Request)} if the custContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-contacts")
    public ResponseEntity<CustContact> createCustContact(@Valid @RequestBody CustContact custContact) throws URISyntaxException {
        log.debug("REST request to save CustContact : {}", custContact);
        if (custContact.getId() != null) {
            throw new BadRequestAlertException("A new custContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustContact result = custContactService.save(custContact);
        return ResponseEntity
            .created(new URI("/api/cust-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-contacts/:id} : Updates an existing custContact.
     *
     * @param id the id of the custContact to save.
     * @param custContact the custContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContact,
     * or with status {@code 400 (Bad Request)} if the custContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-contacts/{id}")
    public ResponseEntity<CustContact> updateCustContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustContact custContact
    ) throws URISyntaxException {
        log.debug("REST request to update CustContact : {}, {}", id, custContact);
        if (custContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustContact result = custContactService.save(custContact);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custContact.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-contacts/:id} : Partial updates given fields of an existing custContact, field will ignore if it is null
     *
     * @param id the id of the custContact to save.
     * @param custContact the custContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContact,
     * or with status {@code 400 (Bad Request)} if the custContact is not valid,
     * or with status {@code 404 (Not Found)} if the custContact is not found,
     * or with status {@code 500 (Internal Server Error)} if the custContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustContact> partialUpdateCustContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustContact custContact
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustContact partially : {}, {}", id, custContact);
        if (custContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustContact> result = custContactService.partialUpdate(custContact);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, custContact.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-contacts} : get all the custContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custContacts in body.
     */
    @GetMapping("/cust-contacts")
    public List<CustContact> getAllCustContacts() {
        log.debug("REST request to get all CustContacts");
        return custContactService.findAll();
    }

    /**
     * {@code GET  /cust-contacts/:id} : get the "id" custContact.
     *
     * @param id the id of the custContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-contacts/{id}")
    public ResponseEntity<CustContact> getCustContact(@PathVariable Long id) {
        log.debug("REST request to get CustContact : {}", id);
        Optional<CustContact> custContact = custContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custContact);
    }

    /**
     * {@code DELETE  /cust-contacts/:id} : delete the "id" custContact.
     *
     * @param id the id of the custContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-contacts/{id}")
    public ResponseEntity<Void> deleteCustContact(@PathVariable Long id) {
        log.debug("REST request to delete CustContact : {}", id);
        custContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
