package com.apptium.web.rest;

import com.apptium.domain.IndContact;
import com.apptium.repository.IndContactRepository;
import com.apptium.service.IndContactService;
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
 * REST controller for managing {@link com.apptium.domain.IndContact}.
 */
@RestController
@RequestMapping("/api")
public class IndContactResource {

    private final Logger log = LoggerFactory.getLogger(IndContactResource.class);

    private static final String ENTITY_NAME = "orderMgmtIndContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndContactService indContactService;

    private final IndContactRepository indContactRepository;

    public IndContactResource(IndContactService indContactService, IndContactRepository indContactRepository) {
        this.indContactService = indContactService;
        this.indContactRepository = indContactRepository;
    }

    /**
     * {@code POST  /ind-contacts} : Create a new indContact.
     *
     * @param indContact the indContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indContact, or with status {@code 400 (Bad Request)} if the indContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-contacts")
    public ResponseEntity<IndContact> createIndContact(@Valid @RequestBody IndContact indContact) throws URISyntaxException {
        log.debug("REST request to save IndContact : {}", indContact);
        if (indContact.getId() != null) {
            throw new BadRequestAlertException("A new indContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndContact result = indContactService.save(indContact);
        return ResponseEntity
            .created(new URI("/api/ind-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-contacts/:id} : Updates an existing indContact.
     *
     * @param id the id of the indContact to save.
     * @param indContact the indContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContact,
     * or with status {@code 400 (Bad Request)} if the indContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-contacts/{id}")
    public ResponseEntity<IndContact> updateIndContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndContact indContact
    ) throws URISyntaxException {
        log.debug("REST request to update IndContact : {}, {}", id, indContact);
        if (indContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndContact result = indContactService.save(indContact);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indContact.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-contacts/:id} : Partial updates given fields of an existing indContact, field will ignore if it is null
     *
     * @param id the id of the indContact to save.
     * @param indContact the indContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContact,
     * or with status {@code 400 (Bad Request)} if the indContact is not valid,
     * or with status {@code 404 (Not Found)} if the indContact is not found,
     * or with status {@code 500 (Internal Server Error)} if the indContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndContact> partialUpdateIndContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndContact indContact
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndContact partially : {}, {}", id, indContact);
        if (indContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContact.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndContact> result = indContactService.partialUpdate(indContact);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, indContact.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-contacts} : get all the indContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indContacts in body.
     */
    @GetMapping("/ind-contacts")
    public List<IndContact> getAllIndContacts() {
        log.debug("REST request to get all IndContacts");
        return indContactService.findAll();
    }

    /**
     * {@code GET  /ind-contacts/:id} : get the "id" indContact.
     *
     * @param id the id of the indContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indContact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-contacts/{id}")
    public ResponseEntity<IndContact> getIndContact(@PathVariable Long id) {
        log.debug("REST request to get IndContact : {}", id);
        Optional<IndContact> indContact = indContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indContact);
    }

    /**
     * {@code DELETE  /ind-contacts/:id} : delete the "id" indContact.
     *
     * @param id the id of the indContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-contacts/{id}")
    public ResponseEntity<Void> deleteIndContact(@PathVariable Long id) {
        log.debug("REST request to delete IndContact : {}", id);
        indContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
